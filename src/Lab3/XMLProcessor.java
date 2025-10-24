package Lab3;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.List;

public class XMLProcessor {

    public static void writeToXML(String outputPath, List<String> tokens,
                                  List<Double> numbers, List<String> timeTokens,
                                  String finalString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("StringProcessing");
        doc.appendChild(root);


        Element tokensElement = doc.createElement("Tokens");
        for (String token : tokens) {
            Element tokenElement = doc.createElement("Token");
            tokenElement.setTextContent(token);
            tokensElement.appendChild(tokenElement);
        }
        root.appendChild(tokensElement);


        Element numbersElement = doc.createElement("Numbers");
        for (Double number : numbers) {
            Element numberElement = doc.createElement("Number");
            numberElement.setTextContent(String.valueOf(number));
            numbersElement.appendChild(numberElement);
        }
        root.appendChild(numbersElement);

        Element resultElement = doc.createElement("Result");
        resultElement.setTextContent(finalString);
        root.appendChild(resultElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(outputPath));
        transformer.transform(source, result);
    }

    public static void readFromXML(String inputPath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(inputPath));

        doc.getDocumentElement().normalize();


        NodeList resultList = doc.getElementsByTagName("Result");
        if (resultList.getLength() > 0) {
            String result = resultList.item(0).getTextContent();
            System.out.println("Result: " + result);
        }

        NodeList tokens = doc.getElementsByTagName("Token");
        System.out.println("Найдено токенов: " + tokens.getLength());
    }
}