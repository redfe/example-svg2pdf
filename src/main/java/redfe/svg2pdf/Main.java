package redfe.svg2pdf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

    public static void main(String[] args) throws Exception {
        convertToPdf();
    }

    public static void convertToPdf() throws Exception {
        String home = "/Users/takayamatetsuya/Study/svg/svg-to-pdf/";
        String svgFilePath = home + "src/main/resources/radarchart.svg";
        String outputFilePath = home + "output/radarchart.pdf";

        try (InputStream in = new FileInputStream(svgFilePath);
                OutputStream out = new FileOutputStream(outputFilePath)) {
            Document document = readDocument(in);
            customize(document);
            TranscoderInput input = new TranscoderInput(document);
            TranscoderOutput output = new TranscoderOutput(out);
            PDFTranscoder transcoder = new PDFTranscoder();
            transcoder.transcode(input, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document readDocument(InputStream in) throws Exception {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = f.createDocument(null, in);
        return doc;
    }

    private static void customize(Document document) {
        Element title1 = document.getElementById("title1");
        removeChildNodes(title1);
        Node newText = document.createTextNode("設定し直しましたよー");
        title1.appendChild(newText);
    }

    private static void removeChildNodes(Node node) {
        NodeList nodeList = node.getChildNodes();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            nodes.add(nodeList.item(i));
        }
        nodes.forEach(n -> node.removeChild(n));
    }

}