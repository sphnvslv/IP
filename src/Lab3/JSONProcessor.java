package Lab3;
import java.io.*;
import java.util.List;

public class JSONProcessor {

    public static void writeToJSON(String outputPath, List<String> tokens,
                                   List<Double> numbers, List<String> timeTokens,
                                   String finalString) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputPath)) {
            writer.println("{");
            writer.println("  \"tokens\": " + tokens + ",");
            writer.println("  \"numbers\": " + numbers + ",");
            writer.println("  \"result\": \"" + finalString + "\"");
            writer.println("}");
        }
    }

    public static void readFromJSON(String inputPath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(inputPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}