import py4j.GatewayServer;

public class JavaServer {

    private PDFSegmenter segmenter;

    public JavaServer() {
        segmenter = new PDFSegmenter();
    }

    public PDFSegmenter getPDFSegmenter() {
        return segmenter;
    }

    public static void main(String[] args) {
        try {
            JavaServer app = new JavaServer();
            GatewayServer server = new GatewayServer(app);
            server.start();
            System.out.println("Py4J Gateway Server Started.");
        } catch (Exception e) {
            System.err.println("Error starting Py4J server: " + e.getMessage());
        }
    }
}
