import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите арифметическое выражение: ");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isRoman = isRomanNumeral(operand1) && isRomanNumeral(operand2);
        boolean isArabic = isArabicNumeral(operand1) && isArabicNumeral(operand2);

        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Неподходящие числа");
        }

        int num1, num2;
        if (isRoman) {
            num1 = convertRomanToArabic(operand1);
            num2 = convertRomanToArabic(operand2);
            if (num1 > 10 || num2 > 10) {
                throw new IllegalArgumentException("Римские числа должны быть от I до X");
            }
        } else {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Арабские числа должны быть от 1 до 10");
            }
        }

        int result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new IllegalArgumentException("Некорректный оператор");
        };

        if (isRoman) {
            if (result < 1) {
                throw new IllegalArgumentException("Результат римской операции не может быть меньше I");
            }
            return convertArabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static boolean isRomanNumeral(String input) {
        List<String> romanNumeralsList = Arrays.asList(romanNumerals);
        return romanNumeralsList.contains(input);
    }

    private static boolean isArabicNumeral(String input) {
        try {
            int value = Integer.parseInt(input);
            return value >= 1 && value <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static final String[] romanNumerals = {
            "I", "II", "III", "IV", "V",
            "VI", "VII", "VIII", "IX", "X"
    };

    private static int convertRomanToArabic(String input) {
        List<String> romanNumeralsList = Arrays.asList(romanNumerals);
        return romanNumeralsList.indexOf(input) + 1;
    }

    private static String convertArabicToRoman(int input) {
        return romanNumerals[input - 1];
    }
}
