package Lab3;
import java.util.*;
import java.text.SimpleDateFormat;

class FileWriter {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void writeToFile(String filename, PyrotechnicsList storage) {
        List<String> lines = new ArrayList<>();
        for (Pyrotechnics item : storage.getItems()) {
            String line = String.format("%s,%s,%s,%.2f,%d,%s,%.2f",
                    item.getId(), item.getType(), item.getModel(),
                    item.getPower(), item.getMaxHeight(),
                    dateFormat.format(item.getProductionDate()),
                    item.getPrice());
            lines.add(line);
        }

        String filePath = "src/Lab3/" + filename;

        try (java.io.PrintWriter writer = new java.io.PrintWriter(filePath)) {
            for (String line : lines) {
                writer.println(line);
            }
            System.out.println("Файл успешно сохранен: " + filePath);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Ошибка записи в файл: " + filePath);
        }
    }
}