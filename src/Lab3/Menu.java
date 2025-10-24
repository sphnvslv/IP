package Lab3;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;

class Menu {
    private Scanner scanner;
    private PyrotechnicsList listStorage;
    private PyrotechnicsMap mapStorage;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public Menu(PyrotechnicsList listStorage, PyrotechnicsMap mapStorage,
                FileReader fileReader, FileWriter fileWriter) {

        this.scanner = new Scanner(System.in);
        this.listStorage = listStorage;
        this.mapStorage = mapStorage;
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Фабрика пиротехники ===");
            System.out.println("1. Показать всю пиротехнику");
            System.out.println("2. Добавить новую пиротехнику");
            System.out.println("3. Редактировать пиротехнику");
            System.out.println("4. Удалить пиротехнику");
            System.out.println("5. Сортировка данных");
            System.out.println("6. Загрузить данные из файла");
            System.out.println("7. Сохранить данные в файл");

            System.out.println("8. Запись в JSON");
            System.out.println("9. Запись в XML");
            System.out.println("10. Шифрование данных");
            System.out.println("11. Создать архивы");
            System.out.println("12. Чтение из JSON");
            System.out.println("13. Чтение из XML");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = getIntInput();
            if (choice == -1)
                continue;

            switch (choice) {
                case 1: displayAllItems();
                 break;
                case 2: createItem();
                 break;
                case 3: updateItem();
                 break;
                case 4: deleteItem();
                 break;
                case 5: showSortMenu();
                 break;
                case 6: loadFromFile();
                 break;
                case 7: saveToFile();
                 break;
                case 8: exportToJSON();
                break;
                case 9: exportToXML();
                break;
                case 10: encryptData();
                break;
                case 11: createArchives();
                break;
                case 12: readFromJSON();
                break;
                case 13: readFromXML();
                break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void exportToJSON() {
        try {
            List<Pyrotechnics> items = listStorage.getItems();
            if (items.isEmpty()) {
                System.out.println("Нет данных для экспорта");
                return;
            }

            List<String> tokens = new ArrayList<>();
            List<Double> numbers = new ArrayList<>();


            for (Pyrotechnics item : items) {
                tokens.add(item.getType() + " - " + item.getModel());
                numbers.add(item.getPower());
                numbers.add((double) item.getMaxHeight());
                numbers.add(item.getPrice());
            }

            String result = "Экспорт данных пиротехники: " + items.size() + " единиц";
            JSONProcessor.writeToJSON("pyrotechnics.json", tokens, numbers, new ArrayList<>(), result);
            System.out.println("Данные экспортированы в pyrotechnics.json (" + items.size() + " единиц)");

        } catch (Exception e) {
            System.out.println("Ошибка экспорта в JSON: " + e.getMessage());
        }
    }

    private void exportToXML() {
        try {
            List<Pyrotechnics> items = listStorage.getItems();
            if (items.isEmpty()) {
                System.out.println("Нет данных для экспорта!");
                return;
            }

            List<String> tokens = new ArrayList<>();
            List<Double> numbers = new ArrayList<>();

            for (Pyrotechnics item : items) {
                tokens.add(item.getId() + ":" + item.getType() + ":" + item.getModel());
                numbers.add(item.getPower());
                numbers.add((double) item.getMaxHeight());
                numbers.add(item.getPrice());
            }

            String result = "Данные о пиротехнике: " + items.size() + " позиций";
            XMLProcessor.writeToXML("pyrotechnics.xml", tokens, numbers, new ArrayList<>(), result);
            System.out.println("Данные экспортированы в pyrotechnics.xml (" + items.size() + " позиций)");

        } catch (Exception e) {
            System.out.println("Ошибка экспорта в XML: " + e.getMessage());
        }
    }

    private void encryptData() {
        try {
            List<Pyrotechnics> items = listStorage.getItems();
            if (items.isEmpty()) {
                System.out.println("Нет данных для шифрования!");
                return;
            }


            StringBuilder dataBuilder = new StringBuilder();
            dataBuilder.append("=== ДАННЫЕ ПИРОТЕХНИКИ ===\n\n");
            for (Pyrotechnics item : items) {
                dataBuilder.append(String.format(
                        "ID: %s\nТип: %s\nМодель: %s\nМощность: %.2f\nВысота: %dм\nЦена: %.2f\nДата: %s\n\n",
                        item.getId(), item.getType(), item.getModel(),
                        item.getPower(), item.getMaxHeight(), item.getPrice(),
                        new SimpleDateFormat("dd.MM.yyyy").format(item.getProductionDate())
                ));
            }

            String originalData = dataBuilder.toString();
            String encrypted = CryptoProcessor.encrypt(originalData);
            String decrypted = CryptoProcessor.decrypt(encrypted);


            StringBuilder resultFile = new StringBuilder();
            resultFile.append("=== Шифрование данных ===\n\n");
            resultFile.append("Исходные:\n").append(originalData).append("\n");
            resultFile.append("Зашифрованные:\n").append(encrypted).append("\n\n");
            resultFile.append("Расшифрованные:\n").append(decrypted).append("\n\n");
            resultFile.append("Статус: ").append(originalData.equals(decrypted) ? "Шифрование работает корректно" : "Ошибка шифрования");

            Files.write(Paths.get("encryption_results.txt"), resultFile.toString().getBytes());

            System.out.println("=== Шифрование завершено ===");
            System.out.println("Все данные сохранены в: encryption_results.txt");
            System.out.println("Обработано: " + items.size() + " единиц пиротехники");
            System.out.println("Статус: " + (originalData.equals(decrypted) ? "Шифрование работает корректно" : "Ошибка шифрования"));

        } catch (Exception e) {
            System.out.println("Ошибка шифрования: " + e.getMessage());
        }
    }

    private void createArchives() {
        try {
            exportToJSON();
            exportToXML();

            ArchiveProcessor.createZipArchive("pyrotechnics.json", "pyrotechnics_data.zip");
            ArchiveProcessor.createJarArchive("pyrotechnics.xml", "pyrotechnics_data.jar");

            System.out.println("=== Архивы созданы ===");
            System.out.println("ZIP архив с данными: pyrotechnics_data.zip");
            System.out.println("JAR архив с данными: pyrotechnics_data.jar");

        } catch (Exception e) {
            System.out.println("Ошибка архивации: " + e.getMessage());
        }
    }

    private void readFromJSON() {
        try {
            System.out.println("=== ЧТЕНИЕ ИЗ JSON ===");

            BufferedReader reader = new BufferedReader(new java.io.FileReader("pyrotechnics.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Файл pyrotechnics.json не найден!");
            System.out.println("Сначала выполните экспорт в JSON (пункт 8)");
        } catch (Exception e) {
            System.out.println("Ошибка чтения JSON: " + e.getMessage());
        }
    }

    private void readFromXML() {
        try {
            System.out.println("=== ЧТЕНИЕ ИЗ XML ===");

            BufferedReader reader = new BufferedReader(new java.io.FileReader("pyrotechnics.xml"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Файл pyrotechnics.xml не найден!");
            System.out.println("Сначала выполните экспорт в XML (пункт 9)");
        } catch (Exception e) {
            System.out.println("Ошибка чтения XML: " + e.getMessage());
        }
    }

    private void displayAllItems() {
        System.out.println("\n=== Вся пиротехника ===");
        if (listStorage.getItems().isEmpty()) {
            System.out.println("Нет данных для отображения");
        } else {
            listStorage.displayAll();
        }
    }

    private void createItem() {
        System.out.println("\n=== Добавление новой пиротехники ===");

        String id = getIdInput();

        if (listStorage.findItem(id) != null) {
            System.out.println("Пиротехника с ID " + id + " уже существует!");
            return;
        }

        String type = selectType();

        System.out.print("Модель: ");
        String model = scanner.nextLine();

        double power = getDoubleInput("Мощность: ");

        int maxHeight = getIntInput("Максимальная высота (м): ");

        Date productionDate = parseDate("Дата производства (дд.мм.гггг): ");

        double price = getDoubleInput("Цена: ");

        Pyrotechnics newItem = new Pyrotechnics(id, type, model, power, maxHeight, productionDate, price);
        listStorage.addItem(newItem);
        mapStorage.addItem(newItem);

        System.out.println("Пиротехника успешно добавлена!");
    }

    private String getIdInput() {
        while (true) {
            System.out.print("ID (только цифры): ");
            String id = scanner.nextLine().trim();

            if (id.matches("\\d+")) {
                return id;
            } else {
                System.out.println("Некорректный ID. Можно вводить только цифры (0-9)");
            }
        }
    }

    private String selectType() {
        while (true) {
            System.out.println("\nВыберите тип пиротехники:");
            System.out.println("1. Петарда");
            System.out.println("2. Салют");
            System.out.println("3. Ракета");
            System.out.println("4. Фонтан");
            System.out.println("5. Фейерверк");
            System.out.print("Ваш выбор (1-5): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: return "Петарда";
                    case 2: return "Салют";
                    case 3: return "Ракета";
                    case 4: return "Фонтан";
                    case 5: return "Фейерверк";
                    default:
                        System.out.println("Неверный выбор. Введите число от 1 до 5");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите число от 1 до 5");
            }
        }
    }

    private void updateItem() {
        String id = getIdInputForUpdate();

        Pyrotechnics item = listStorage.findItem(id);
        if (item == null) {
            System.out.println("Пиротехника с ID " + id + " не найдена!");
            return;
        }

        System.out.println("Текущие данные: " + item);
        System.out.println("\nВведите новые данные (оставьте пустым для сохранения текущего значения):");

        System.out.println("Тип [" + item.getType() + "]:");
        System.out.println("0. Оставить текущий");
        System.out.println("1. Петарда");
        System.out.println("2. Салют");
        System.out.println("3. Ракета");
        System.out.println("4. Фонтан");
        System.out.println("5. Фейерверк");
        System.out.print("Ваш выбор (0-5): ");

        String typeChoice = scanner.nextLine();
        if (!typeChoice.isEmpty() && !typeChoice.equals("0")) {
            switch (typeChoice) {
                case "1": item.setType("Петарда");
                 break;
                case "2": item.setType("Салют");
                 break;
                case "3": item.setType("Ракета");
                 break;
                case "4": item.setType("Фонтан");
                 break;
                case "5": item.setType("Фейерверк");
                 break;
                default: System.out.println("Неверный выбор, тип не изменен");
            }
        }

        System.out.print("Модель [" + item.getModel() + "]: ");
        String model = scanner.nextLine();
        if (!model.isEmpty())
            item.setModel(model);

        System.out.print("Мощность [" + item.getPower() + "]: ");
        String powerStr = scanner.nextLine();
        if (!powerStr.isEmpty()) {
            try {
                item.setPower(Double.parseDouble(powerStr));
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение мощности");
            }
        }

        System.out.print("Максимальная высота [" + item.getMaxHeight() + "]: ");
        String heightStr = scanner.nextLine();
        if (!heightStr.isEmpty()) {
            try {
                item.setMaxHeight(Integer.parseInt(heightStr));
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение высоты");
            }
        }

        System.out.print("Цена [" + item.getPrice() + "]: ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) {
            try {
                item.setPrice(Double.parseDouble(priceStr));
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение цены");
            }
        }

        System.out.println("Данные успешно обновлены!");
    }

    private String getIdInputForUpdate() {
        while (true) {
            System.out.print("\nВведите ID для редактирования (только цифры): ");
            String id = scanner.nextLine().trim();

            if (id.matches("\\d+")) {
                return id;
            } else {
                System.out.println("Некорректный ID. Можно вводить только цифры (0-9).");
            }
        }
    }

    private void deleteItem() {
        System.out.print("\nВведите ID для удаления: ");
        String id = scanner.nextLine();

        Pyrotechnics item = listStorage.findItem(id);
        if (item == null) {
            System.out.println("Пиротехника с ID " + id + " не найдена");
            return;
        }

        System.out.println("Будет удалено: " + item);
        System.out.print("Вы уверены? (Да/Нет): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("да")) {
            listStorage.removeItem(id);
            mapStorage.removeItem(id);
            System.out.println("Пиротехника с ID " + id + " удалена");
        } else {
            System.out.println("Удаление отменено");
        }
    }

    private void showSortMenu() {
        System.out.println("\n=== Сортировка ===");
        System.out.println("1. По цене (List)");
        System.out.println("2. По мощности (List)");
        System.out.println("3. По высоте (List)");
        System.out.println("4. По дате производства (List)");
        System.out.println("5. По типу и модели (List)");
        System.out.println("6. По цене (Map)");
        System.out.print("Выберите тип сортировки: ");

        int choice = getIntInput();
        if (choice == -1)
            return;

        switch (choice) {
            case 1:
                sortListByPrice();
                System.out.println("Отсортировано по цене:");
                break;
            case 2:
                sortListByPower();
                System.out.println("Отсортировано по мощности:");
                break;
            case 3:
                sortListByHeight();
                System.out.println("Отсортировано по высоте:");
                break;
            case 4:
                sortListByDate();
                System.out.println("Отсортировано по дате:");
                break;
            case 5:
                sortListByTypeAndModel();
                System.out.println("Отсортировано по типу и модели:");
                break;
            case 6:
                displaySortedMap();
                return;
            default:
                System.out.println("Неверный выбор");
                return;
        }
        listStorage.displayAll();
    }

    private void sortListByPrice() {
        List<Pyrotechnics> items = listStorage.getItems();
        Collections.sort(items,
                (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())
        );
    }

    private void sortListByPower() {
        List<Pyrotechnics> items = listStorage.getItems();
        Collections.sort(items,
                (p1, p2) -> Double.compare(p1.getPower(), p2.getPower())
        );
    }

    private void sortListByHeight() {
        List<Pyrotechnics> items = listStorage.getItems();
        Collections.sort(items,
                (p1, p2) -> Integer.compare(p1.getMaxHeight(), p2.getMaxHeight())
        );
    }

    private void sortListByDate() {
        List<Pyrotechnics> items = listStorage.getItems();
        Collections.sort(items,
                (p1, p2) -> p1.getProductionDate().compareTo(p2.getProductionDate())
        );
    }

    private void sortListByTypeAndModel() {
        List<Pyrotechnics> items = listStorage.getItems();
        Collections.sort(items,
                (p1, p2) -> {
                    int typeCompare = p1.getType().compareTo(p2.getType());
                    if (typeCompare != 0)
                        return typeCompare;
                    return p1.getModel().compareTo(p2.getModel());
                }
        );
    }

    private void displaySortedMap() {
        System.out.println("\n=== Отсортированная Map по цене ===");
        Map<String, Pyrotechnics> sortedMap = mapStorage.getSortedByPrice();
        if (sortedMap.isEmpty()) {
            System.out.println("Нет данных для отображения");
        } else {
            for (Map.Entry<String, Pyrotechnics> entry : sortedMap.entrySet()) {
                System.out.println(entry.getValue());
            }
        }
    }

    private void loadFromFile() {
        System.out.print("Введите имя файла для загрузки: ");
        String filename = scanner.nextLine();
        fileReader.readFromFile(filename, listStorage, mapStorage);
        System.out.println("Данные загружены");
    }

    private void saveToFile() {
        System.out.print("Введите имя файла для сохранения: ");
        String filename = scanner.nextLine();
        fileWriter.writeToFile(filename, listStorage);
        System.out.println("Данные сохранены");
    }

    private Date parseDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                sdf.setLenient(false);

                Date date = sdf.parse(dateStr);

                Date currentDate = new Date();
                if (date.after(currentDate)) {
                    System.out.println("Дата не может быть в будущем. Введите корректную дату: ");
                    continue;
                }

                return date;

            } catch (ParseException e) {
                System.out.println("Некорректная дата. Введите существующую дату в формате дд.мм.гггг: ");
            }
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите число");
            }
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Значение не может быть отрицательным. Введите положительное число");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите целое число");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine();
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Значение не может быть отрицательным. Введите положительное число");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите число");
            }
        }
    }
}