package Lab2;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.SimpleDateFormat;

public class StringProcessor {

    public static void main(String[] args) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

        String firstLine = fileReader.readLine();
        String delimiters = fileReader.readLine();
        fileReader.close();

        Scanner scanner = new Scanner(System.in);
        String searchCharLine = "";
        while (searchCharLine.isEmpty()) {
            System.out.print("Введите символы для поиска: ");
            searchCharLine = scanner.nextLine().trim();
            if (searchCharLine.isEmpty()) {
                System.out.println("Ошибка: строка не может быть пустой");
            }
        }
        scanner.close();

        List<String> tokens;
        if (delimiters.length() == 1) {
            tokens = Arrays.asList(firstLine.split(Pattern.quote(delimiters)));
        } else {
            tokens = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(firstLine, delimiters);
            while (tokenizer.hasMoreTokens()) {
                tokens.add(tokenizer.nextToken());
            }
        }

        List<Double> numbers = new ArrayList<>();
        List<String> nonNumberTokens = new ArrayList<>();

        for (String token : tokens) {
            try {
                double num = Double.parseDouble(token);
                numbers.add(num);
            } catch (NumberFormatException e) {
                nonNumberTokens.add(token);
            }
        }

        List<String> timeTokens = new ArrayList<>();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm");
        timeFormat.setLenient(false);

        for (String token : nonNumberTokens) {
            try {
                timeFormat.parse(token);
                timeTokens.add(token);
            } catch (Exception e) {
            }
        }

        timeTokens.sort((time1, time2) -> {
            String[] parts1 = time1.split("-");
            String[] parts2 = time2.split("-");
            int compareHours = parts1[0].compareTo(parts2[0]);
            return compareHours != 0 ? compareHours : parts1[1].compareTo(parts2[1]);
        });

        StringBuilder resultBuilder = new StringBuilder(firstLine);
        Random random = new Random();
        double randomNumber = random.nextDouble() * 100;
        String randomStr = String.format("%.2f", randomNumber).replace(',', '.');

        if (!numbers.isEmpty()) {
            String lastNumber = numbers.get(numbers.size() - 1).toString();
            int lastNumIndex = firstLine.lastIndexOf(lastNumber);
            if (lastNumIndex != -1) {
                resultBuilder.insert(lastNumIndex + lastNumber.length(), " " + randomStr);
            }
        } else {
            int middleIndex = resultBuilder.length() / 2;
            resultBuilder.insert(middleIndex, " " + randomStr + " ");
        }

        String currentString = resultBuilder.toString();
        String shortestSubstring = findShortestSubstring(currentString, searchCharLine);
        if (shortestSubstring != null) {
            int pos = currentString.indexOf(shortestSubstring);
            if (pos != -1) {
                resultBuilder.delete(pos, pos + shortestSubstring.length());
            }
        }

        String results = formatResults(tokens, numbers, timeTokens, resultBuilder.toString(), searchCharLine, firstLine);
        writer.write(results);
        writer.close();
        System.out.println("Результаты в output.txt");
    }

    public static String findShortestSubstring(String input, String searchChars) {
        int firstOccurrenceIndex = Integer.MAX_VALUE;

        for (char ch : searchChars.toCharArray()) {
            int index = input.indexOf(ch);
            if (index != -1 && index < firstOccurrenceIndex) {
                firstOccurrenceIndex = index;
            }
        }

        if (firstOccurrenceIndex != Integer.MAX_VALUE) {
            return input.substring(0, firstOccurrenceIndex + 1);
        }
        return null;
    }

    public static String formatResults(List<String> tokens, List<Double> numbers,
                                       List<String> timeTokens, String finalString,
                                       String searchChars, String originalString) {
        StringBuilder result = new StringBuilder();
        result.append("Исходная строка: ").append(originalString).append("\n");
        result.append("Символы для поиска: ").append(searchChars).append("\n");
        result.append("Все лексемы: ").append(tokens).append("\n");
        result.append("Вещественные числа: ").append(numbers).append("\n");
        result.append("Время: ").append(timeTokens).append("\n");
        result.append("Итоговая строка: ").append(finalString).append("\n");
        return result.toString();
    }
}