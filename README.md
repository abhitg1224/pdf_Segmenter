# PDF Content Segmenter

## Project Overview

This project is a **PDF Content Segmenter** developed using **iText** in **Java** and controlled via **Python** using **Py4J**. The application segments a system-generated PDF into distinct sections based on the whitespace between blocks of text, making exactly `X` cuts. The goal is to identify logical sections, such as headings, paragraphs, or distinct content blocks, that are visually separated by increased whitespace without using image processing techniques.

---

## Technologies Used

- **Java 8+**
- **Maven** (for dependency management)
- **iText 7** (for PDF manipulation)
- **Py4J** (for Python-to-Java communication)
- **Python 3.7+**

---

## Prerequisites

Before running the project, ensure you have the following installed:

1. **Java 8 or later**
2. **Maven**: Download and install Maven from [here](https://maven.apache.org/install.html).
3. **Python 3.7+**: You can download it from [python.org](https://www.python.org/downloads/).

---

## Setting Up the Project

### 1. Clone the Repository

First, clone the repository from GitHub:

`git clone https://github.com/your-username/pdf-segmenter.git`

### 2. Install Maven Dependencies
Maven will automatically download all the dependencies specified in the pom.xml file, including iText and Py4J.

Run the following command to download the dependencies and compile the Java code:

`mvn clean install`

### 3. Install Python Dependencies

The Python script relies on the Py4J library for communication with Java. Install Py4J for Python using pip:

`pip install py4j`

---

### Running the Project

- Start the Java Server (Py4J Gateway)

In order to allow Python to communicate with Java, you must first start the JavaServer that serves as the Py4J gateway.

Run the following command from the project root to start the server:


`mvn exec:java -Dexec.mainClass="com.example.JavaServer"`


- Run the Python Script

After the Java server is up and running, you can run the Python script that performs the PDF segmentation.

`python python/pdf_segmenter.py`


This will:

Connect to the JavaServer (via Py4J).

Perform the PDF segmentation based on whitespace logic.

Output the segmented PDFs to the specified directory.

---

### Example Usage

- Scenario: Segmenting a PDF Named **document.pdf**
Let's assume you have a PDF file named document.pdf that you want to split into 4 segments based on the whitespace between content blocks.

- Modify the Python Script

First, open the python/pdf_segmenter.py script and modify the following variables:

Specify the input PDF file

input_pdf = 'document.pdf'  # Path to the input PDF file

Specify the output folder where segmented PDFs will be saved

output_folder = 'output_segments'

Set the number of segments (cuts) you want to make

num_segments = 4  # This will create 4 segments

- Run the Python Script

After modifying the script, run the Python script to trigger the segmentation process.

`python python/pdf_segmenter.py`

- 4. Expected Output

The Python script will connect to the JavaServer via Py4J and perform the segmentation based on the significant whitespace found in document.pdf. The resulting segmented PDFs will be saved in the output_segments folder.

Output Files:
segment_1.pdf
segment_2.pdf
segment_3.pdf
segment_4.pdf

Each of these files will represent a section of the original document.pdf based on the largest whitespace found between content blocks.