package Lab3;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class FileReader {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public List<Pyrotechnics> readFromFile(String filename, PyrotechnicsList listStorage,
                                           PyrotechnicsMap mapStorage) {
        List<Pyrotechnics> validItems = new ArrayList<>();

        String filePath = "src/Lab3/" + filename;

        try (Scanner scanner = new Scanner(new java.io.File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Pyrotechnics item = parseAndValidateLine(line);
                    validItems.add(item);
                    listStorage.addItem(item);
                    mapStorage.addItem(item);
                } catch (Exception e) {
                    logError("Некорректная строка: " + line + " - " + e.getMessage());
                }
            }
        } catch (java.io.FileNotFoundException e) {
            logError("Файл не найден: " + filePath);
        }

        return validItems;
    }

    private Pyrotechnics parseAndValidateLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Неверное количество полей");
        }

        String id = validateId(parts[0].trim());
        String type = validateType(parts[1].trim());
        String model = validateModel(parts[2].trim());
        double power = validatePower(parts[3].trim());
        int maxHeight = validateMaxHeight(parts[4].trim());
        Date productionDate = validateDate(parts[5].trim());
        double price = validatePrice(parts[6].trim());

        return new Pyrotechnics(id, type, model, power, maxHeight, productionDate, price);
    }

    private String validateId(String id) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("ID не может быть пустым");
        return id;
    }

    private String validateType(String type) {
        if (type == null || type.isEmpty())
            throw new IllegalArgumentException("Тип не может быть пустым");
        return type;
    }

    private String validateModel(String model) {
        if (model == null || model.isEmpty())
            throw new IllegalArgumentException("Модель не может быть пустой");
        return model;
    }

    private double validatePower(String powerStr) {
        try {
            double power = Double.parseDouble(powerStr);
            if (power < 0)
                throw new IllegalArgumentException("Мощность не может быть отрицательной");
            return power;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное значение мощности: " + powerStr);
        }
    }

    private int validateMaxHeight(String heightStr) {
        try {
            int height = Integer.parseInt(heightStr);
            if (height < 0)
                throw new IllegalArgumentException("Высота не может быть отрицательной");
            return height;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное значение высоты: " + heightStr);
        }
    }

    private Date validateDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты: " + dateStr);
        }
    }

    private double validatePrice(String priceStr) {
        try {
            double price = Double.parseDouble(priceStr);
            if (price < 0)
                throw new IllegalArgumentException("Цена не может быть отрицательной");
            return price;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное значение цены: " + priceStr);
        }
    }

    private void logError(String errorMessage) {
        String timestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
        String logMessage = timestamp + " - " + errorMessage;
        System.err.println(logMessage);

        try (java.io.PrintWriter writer = new java.io.PrintWriter(
                new java.io.FileWriter("error.log", true))) {
            writer.println(logMessage);
        } catch (java.io.IOException e) {
            System.err.println("Ошибка записи в лог-файл: " + e.getMessage());
        }
    }
}