import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.TextChunk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFSegmenter {

    // Constants
    private static final float NORMAL_LINE_SPACING = 12.0f; // Approximate normal line spacing

    // Method to detect whitespace and segment PDF
    public void segmentPDF(String inputPdfPath, String outputDir, int numCuts) {
        try {
            // Check if input PDF exists
            File inputFile = new File(inputPdfPath);
            if (!inputFile.exists()) {
                throw new IOException("Input PDF file not found: " + inputPdfPath);
            }

            // Ensure output directory exists
            File outputDirectory = new File(outputDir);
            if (!outputDirectory.exists()) {
                if (!outputDirectory.mkdirs()) {
                    throw new IOException("Failed to create output directory: " + outputDir);
                }
            }

            PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputPdfPath));
            int totalPages = pdfDoc.getNumberOfPages();
            if (totalPages == 0) {
                throw new IOException("The PDF contains no pages.");
            }

            // Get positions where cuts should be made based on whitespace and paragraph breaks
            List<Integer> cutPoints = getWhitespacePositions(pdfDoc, totalPages, numCuts);

            // Create segmented PDFs based on identified cut points
            for (int i = 0; i <= numCuts; i++) {
                int startPage = (i == 0) ? 1 : cutPoints.get(i - 1);
                int endPage = (i == numCuts) ? totalPages : cutPoints.get(i);

                PdfDocument segmentDoc = new PdfDocument(new PdfWriter(outputDir + "/segment_" + (i + 1) + ".pdf"));
                pdfDoc.copyPagesTo(startPage, endPage, segmentDoc);
                segmentDoc.close();
            }

            pdfDoc.close();
            System.out.println("PDF segmented successfully.");

        } catch (IOException e) {
            System.err.println("Error while processing the PDF: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Method to find significant whitespace or paragraph breaks between pages
    private List<Integer> getWhitespacePositions(PdfDocument pdfDoc, int totalPages, int numCuts) throws IOException {
        List<Integer> cutPoints = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            // Extract text content and its positions from the page
            LocationTextExtractionStrategy strategy = new LocationTextExtractionStrategy();
            String pageText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i), strategy);

            // Check if page is mostly whitespace or has significant vertical gaps between text blocks
            if (hasSignificantWhitespaceOrGaps(strategy)) {
                cutPoints.add(i);  // Add this page as a potential cut point
            }
        }

        // Ensure exactly numCuts are made based on the largest gaps found
        if (cutPoints.size() < numCuts) {
            throw new IOException("Not enough significant whitespace or paragraph breaks found to make cuts.");
        }

        return cutPoints.subList(0, numCuts);  // Return exactly the number of cuts needed
    }

    // Method to check for significant whitespace or gaps between paragraphs
    private boolean hasSignificantWhitespaceOrGaps(LocationTextExtractionStrategy strategy) {
        List<TextChunk> textChunks = strategy.getResultantTextChunks();

        // Loop through chunks and calculate vertical spacing between them
        for (int i = 1; i < textChunks.size(); i++) {
            float prevBottom = textChunks.get(i - 1).getBottom();
            float currentTop = textChunks.get(i).getTop();
            float lineSpacing = currentTop - prevBottom;  // Calculate spacing between two lines
            
            // Define a threshold for what qualifies as significant whitespace
            if (lineSpacing > 2 * NORMAL_LINE_SPACING) {
                return true;  // Significant gap found (likely between paragraphs)
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String inputPdf = "input.pdf";  // Replace with your actual PDF file path
        String outputDir = "output_segments";  // Output directory
        int numCuts = 3;  // Replace with desired number of cuts

        PDFSegmenter segmenter = new PDFSegmenter();
        segmenter.segmentPDF(inputPdf, outputDir, numCuts);
    }
}
