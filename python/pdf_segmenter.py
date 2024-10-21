from py4j.java_gateway import JavaGateway, GatewayParameters
import os

def segment_pdf(input_pdf, output_folder, num_segments):
    try:
        # Check if input PDF exists
        if not os.path.exists(input_pdf):
            raise FileNotFoundError(f"Input PDF file not found: {input_pdf}")

        # Ensure output folder exists
        if not os.path.exists(output_folder):
            os.makedirs(output_folder)

        # Start the Py4J gateway
        gateway = JavaGateway(gateway_parameters=GatewayParameters(port=25333))
        
        # Get the Java PDFSegmenter instance
        pdf_segmenter = gateway.entry_point.getPDFSegmenter()
        
        # Call the Java method to segment the PDF
        pdf_segmenter.segmentPDF(input_pdf, output_folder, num_segments)
        print(f"Segmented PDF into {num_segments} sections.")
        
        # Shutdown the gateway after processing is done
        gateway.shutdown()

    except FileNotFoundError as e:
        print(f"Error: {e}")
    except OSError as e:
        print(f"File system error: {e}")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")

if __name__ == "__main__":
    input_pdf = 'input.pdf'  # Replace with your actual PDF path
    output_folder = 'output_segments'
    num_segments = 3  # Replace with the desired number of segments

    # Start segmentation
    segment_pdf(input_pdf, output_folder, num_segments)
