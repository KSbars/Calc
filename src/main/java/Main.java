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
            throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
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
            if (num1 == 0 || num1 > 10 || num2 == 0 || num2 > 10) {
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
        String romanNumeralPattern = "^(?=[MDCLXVI])(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        return input.matches(romanNumeralPattern);
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
            "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV",
            "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII", "XXIV", "XXV",
            "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
            "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV",
            "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
            "XLI", "XLII", "XLIII", "XLIV", "XLV",
            "XLVI", "XLVII", "XLVIII", "XLIX", "L",
            "LI", "LII", "LIII", "LIV", "LV",
            "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV",
            "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
            "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV",
            "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
            "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV",
            "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
            "XCI", "XCII", "XCIII", "XCIV", "XCV",
            "XCVI", "XCVII", "XCVIII", "XCIX", "C"
    };

    private static int convertRomanToArabic(String input) {
        List<String> romanNumeralsList = Arrays.asList(romanNumerals);
        return romanNumeralsList.indexOf(input) + 1;
    }

    private static String convertArabicToRoman(int input) {
        return romanNumerals[input - 1];
    }
}
