import unittest
import os
from pdf_segmenter import segment_pdf

class TestPDFSegmenter(unittest.TestCase):

    def setUp(self):
        """Set up the necessary input and output paths for the tests."""
        self.valid_input_pdf = 'sample.pdf'  # Path to a valid test PDF
        self.output_folder = 'test_segments'  # Folder to store output segments
        self.num_segments = 3  # Desired number of segments for splitting

        # Ensure the output folder exists or create it
        if not os.path.exists(self.output_folder):
            os.makedirs(self.output_folder)

    def tearDown(self):
        """Clean up the output folder after tests."""
        # Remove all generated output files
        for f in os.listdir(self.output_folder):
            if f.endswith('.pdf'):
                os.remove(os.path.join(self.output_folder, f))
        # Optionally remove the directory itself
        # os.rmdir(self.output_folder)

    def test_segmentation_valid_input(self):
        """Test segmenting a valid PDF with the correct number of segments."""
        segment_pdf(self.valid_input_pdf, self.output_folder, self.num_segments)
        generated_files = [f for f in os.listdir(self.output_folder) if f.endswith('.pdf')]
        
        # Verify that the expected number of segment files were created
        self.assertEqual(len(generated_files), self.num_segments)
    
    def test_invalid_input_file(self):
        """Test that the program raises an error when the input PDF does not exist."""
        with self.assertRaises(FileNotFoundError):
            segment_pdf('nonexistent.pdf', self.output_folder, self.num_segments)

    def test_invalid_output_folder(self):
        """Test handling of an invalid output folder path."""
        invalid_output_folder = '/invalid_path/output_segments'
        with self.assertRaises(OSError):
            segment_pdf(self.valid_input_pdf, invalid_output_folder, self.num_segments)

    def test_empty_pdf(self):
        """Test handling of an empty PDF file (no pages)."""
        empty_pdf = 'empty.pdf'  # A PDF with no pages
        # Assume this file exists and is empty
        with self.assertRaises(Exception):
            segment_pdf(empty_pdf, self.output_folder, self.num_segments)

    def test_not_enough_whitespace(self):
        """Test if the program raises an error when there is not enough whitespace to make cuts."""
        pdf_with_no_whitespace = 'no_whitespace.pdf'  # A PDF with continuous content and no significant whitespace
        
        # This should raise an error because there aren't enough significant whitespaces to make the cuts
        with self.assertRaises(Exception):
            segment_pdf(pdf_with_no_whitespace, self.output_folder, self.num_segments)

    def test_too_many_requested_segments(self):
        """Test when the requested number of segments exceeds the number of available significant whitespace positions."""
        # Use a PDF with only 1 or 2 significant whitespace gaps
        pdf_with_few_whitespaces = 'few_whitespaces.pdf'  # Assume this file has only 2 significant whitespaces
        
        # Requesting more segments than available whitespace gaps should raise an error
        with self.assertRaises(Exception):
            segment_pdf(pdf_with_few_whitespaces, self.output_folder, 5)

    def test_zero_segments(self):
        """Test behavior when zero segments are requested."""
        with self.assertRaises(ValueError):
            segment_pdf(self.valid_input_pdf, self.output_folder, 0)

    def test_large_number_of_segments(self):
        """Test behavior when a very large number of segments are requested."""
        # Requesting a huge number of segments should fail since there won't be that many whitespace gaps
        with self.assertRaises(Exception):
            segment_pdf(self.valid_input_pdf, self.output_folder, 1000)

    def test_single_segment(self):
        """Test behavior when only 1 segment is requested."""
        segment_pdf(self.valid_input_pdf, self.output_folder, 1)
        generated_files = [f for f in os.listdir(self.output_folder) if f.endswith('.pdf')]

        # When 1 segment is requested, we expect only one output file (the full PDF).
        self.assertEqual(len(generated_files), 1)

if __name__ == '__main__':
    unittest.main()
