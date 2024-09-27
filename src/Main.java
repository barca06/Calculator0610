import java.util.Scanner;

public class Main {
    static int leftit;
    static int rightit;
    static String op;
    static String[] array;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) { // Бесконечный цикл для повторения запроса на ввод
            System.out.println("Введите пример (например, 5 + 3 или I * V):");
            String input = scanner.nextLine().trim();

            if (!input.matches("^\\w+\\s*[+\\-*/]\\s*\\w+$")) {
                System.out.println("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                break; // Завершаем программу при ошибке
            }

            array = input.split("\\s+");

            boolean isLeftRoman = isRoman(array[0]);
            boolean isRightRoman = isRoman(array[2]);

            if (isLeftRoman != isRightRoman) {
                System.out.println("Нельзя использовать одновременно разные системы счисления");
                break; // Завершаем программу при ошибке
            }

            leftit = isLeftRoman ? romanToArabic(array[0]) : Integer.parseInt(array[0]);
            rightit = isRightRoman ? romanToArabic(array[2]) : Integer.parseInt(array[2]);

            if (leftit <= 0 || leftit > 10 || rightit <= 0 || rightit > 10) {
                System.out.println("Числа должны быть от 1 до 10");
                break; // Завершаем программу при ошибке
            }

            op = array[1];
            String result = calc();
            System.out.println(result);
        }
    }

    static String calc() {
        int result;
        switch (op) {
            case "+": result = leftit + rightit; break;
            case "-": result = leftit - rightit; break;
            case "*": result = leftit * rightit; break;
            case "/":
                if (rightit == 0) return "Делить на ноль нельзя";
                result = leftit / rightit;
                break;
            default: return "Неизвестная операция";
        }

        if (result < 1 && isRoman(array[0])) {
            return "Результат не может быть отрицательным или равен нулю в римской системе";
        }

        return isRoman(array[0]) ? arabicToRoman(result) : String.valueOf(result);
    }

    static boolean isRoman(String str) {
        return str.matches("[IVXLCDM]+");
    }

    static int romanToArabic(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = charToValue(roman.charAt(i));
            int next = (i + 1 < roman.length()) ? charToValue(roman.charAt(i + 1)) : 0;
            result += (current < next) ? -current : current;
        }
        return result;
    }

    static int charToValue(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            default: return 0;
        }
    }

    static String arabicToRoman(int number) {
        if (number <= 0) return "";

        StringBuilder roman = new StringBuilder();
        String[] romanSymbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                roman.append(romanSymbols[i]);
                number -= values[i];
            }
        }

        return roman.toString();
    }
}