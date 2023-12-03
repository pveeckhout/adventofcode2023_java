package be.pietervaneeckhout.adventofcode.adventofcode._2023.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {

    public static void main(String[] args) {
        System.out.printf("CalibrationValueForDocument: %d%n", new Day1().getCalibrationValueForDocument(Day1Input.input));
    }

    public int getCalibrationValueForDocument(String input) {
        return input.lines()
                .map(this::getCalibrationValueForLine)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getCalibrationValueForLine(String input) {
        List<String> digits = getDigits(input);

        if (digits.isEmpty()) {
            throw new IllegalArgumentException("No numbers found in line %s".formatted(input));
        }

        return Integer.parseInt(mapLiteralToDigit(digits.getFirst()) + mapLiteralToDigit(digits.getLast()));
    }

    private List<String> getDigits(String input) {
        List<String> digits = new ArrayList<>();
        Pattern pattern = Pattern.compile("(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)");

        StringBuilder currentLiteral = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i); // Get the character at index i
            if (Character.isDigit(ch)) {
                currentLiteral = new StringBuilder();
                digits.add(String.valueOf(ch));
            } else {
                currentLiteral.append(ch);
                String currentLiteralString = currentLiteral.toString();

                Matcher matcher = pattern.matcher(currentLiteralString);
                if (matcher.find()) {
                    digits.add(matcher.group());

                    if (currentLiteralString.endsWith("e") || currentLiteralString.endsWith("t") || currentLiteralString.endsWith("o")) {
                        i--;
                    }

                    currentLiteral = new StringBuilder();
                }
            }
        }

        return digits;
    }

    private String mapLiteralToDigit(String digit) {
        return switch (digit) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> digit;
        };
    }
}
