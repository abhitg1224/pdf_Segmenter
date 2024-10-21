# PDF Content Segmenter

## Project Overview

This project is a **PDF Content Segmenter** developed using **iText** in **Java** and controlled via **Python** using **Py4J**. The application programmatically segments a system-generated PDF into distinct sections based on whitespace between blocks of text, making exactly `X` cuts. The goal is to identify logical sections such as headings, paragraphs, or distinct content blocks that are visually separated by significant whitespace.

---

## Technologies Used

- **Python 3.7+**
- **Java 8+**
- **iText 7 (for PDF manipulation)**
- **Py4J (to bridge Python and Java)**

---

## Setup Instructions

### 1. Install Python Dependencies

- You need to install the Py4J library, which allows communication between Python and Java.

  **pip install py4j**


### 2. Download the iText and Py4J JARs
- iText JAR: Download the iText JAR.
- Py4J JAR: Download the Py4J JAR.

### 3. Compile the Java Code
- Place the iText and Py4J JARs in the same directory as your Java code and compile the Java classes.

  `javac -cp .:itext7-core.jar:py4j0.x.jar PDFSegmenter.java JavaServer.java`

  This command compiles the Java classes and includes the iText and Py4J libraries in the classpath.

### 4. Start the Py4J Java Gateway
- Run the JavaServer class to start the Py4J gateway. This will allow the Python script to communicate with Java.

  `java -cp .:itext7-core.jar:py4j0.x.jar JavaServer`

  Once the gateway is running, it will wait for requests from the Python script.

---

### How to Run the Application

### 1. Prepare Input Files
Ensure that your input PDF file is available in the project directory (or provide the correct file path). The application will segment this PDF based on significant whitespace and output the segments into a specified folder.

### 2. Run the Python Script
The Python script pdf_segmenter.py acts as the controller and uses Py4J to invoke the Java-based PDF segmentation logic.

You can run the script as follows:
**python pdf_segmenter.py**

By default, the script is configured to:
Process a file named **input.pdf**.

Output the segmented PDFs in the **output_segments** folder.
Make 3 cuts (producing 4 segments).

### 3. Specify Custom Parameters
You can modify the script to process a different input file, output directory, or number of segments by passing those parameters directly:

input_pdf = 'your-input-file.pdf'  # Replace with your PDF file path

output_folder = 'your-output-folder'

num_segments = 3  # Replace with desired number of segments

If you want to change the number of segments, simply adjust the num_segments variable in the script.

---

### Example Usage

- **Basic Example**

To segment a PDF named input.pdf into 3 segments, run the Python script like this:

**python pdf_segmenter.py**

The output will be saved in the output_segments folder as segment_1.pdf, segment_2.pdf, etc.

- **Custom Example**

If your input file is named document.pdf, and you want to segment it into 5 sections, modify the script like this:


input_pdf = 'document.pdf'
output_folder = 'output_folder'
num_segments = 5

Then run the script again:
python pdf_segmenter.py

This will create 5 segments of the input file and store them in the output_folder directory.

