package com.ishmaev.artur;
/***
 * @autor artur ishmaev
 */

import java.util.*;

public class Main {
    public static final Map<Integer, String> ROMAN_NUMBERS;
    public static final Set<String> symbols;
    enum TypeNum {
        ARABIC,
        ROMAN
    }
    static {
        ROMAN_NUMBERS = new LinkedHashMap<>();
        ROMAN_NUMBERS.put(1, "I");
        ROMAN_NUMBERS.put(2, "II");
        ROMAN_NUMBERS.put(3, "III");
        ROMAN_NUMBERS.put(4, "IV");
        ROMAN_NUMBERS.put(5, "V");
        ROMAN_NUMBERS.put(6, "VI");
        ROMAN_NUMBERS.put(7, "VII");
        ROMAN_NUMBERS.put(8, "VIII");
        ROMAN_NUMBERS.put(9, "IX");
        ROMAN_NUMBERS.put(10, "X");

        symbols = new HashSet<>();
        symbols.add("+");
        symbols.add("-");
        symbols.add("*");
        symbols.add("/");
    }

    public static void main(String[] args) {
        calc();
    }

    private static void calc() {
        List<String> numbers = new ArrayList<>();
        Scanner scannerConsole = new Scanner(System.in);
        int result = 0;
        int exit = 1;
        System.out.println("Welcome to a simple calculator.\n" +
                "The calculator can add, subtract, multiply and divide integers from 0 to 10.\n" +
                "You can use both Arabic and Roman numerals.\n" +
                "enter 0 to exit");

        while (exit != 0) {
            System.out.print("Enter an arithmetic expression: ");
            int a, b;
            boolean flagRoman = false;//нужен для определения формата вывода результата
            String str = scannerConsole.nextLine();
            if (str.equals("1")) break;
            CalcFactory calcFactory;
            //Второй сканнер нужен для того чтобы считать по слову со строки методом next()
            // 1 - первое число 2 - операнд 3 - второе число
            Scanner scannerStr = new Scanner(str);
            while (scannerStr.hasNext()) {
                numbers.add(scannerStr.next());
            }
            if (numbers.size() != 3) throw new RuntimeException("The number of variables should be two!");
            if (!symbols.contains(numbers.get(1)))
                throw new RuntimeException("A string is not a mathematical operation!");
            //проверка на число и создание нужной фабрики
            if (isNumericRomanNum(numbers.get(0)) & isNumericRomanNum(numbers.get(2))) {
                calcFactory = createCalcByType(TypeNum.ROMAN);
                a = findKeyByValue(ROMAN_NUMBERS, numbers.get(0));
                b = findKeyByValue(ROMAN_NUMBERS, numbers.get(2));
                flagRoman = true;
            } else if (isNumeric(numbers.get(0)) & isNumeric(numbers.get(2))) {
                calcFactory = createCalcByType(TypeNum.ARABIC);
                a = Integer.parseInt(numbers.get(0));
                b = Integer.parseInt(numbers.get(2));
            } else
                throw new RuntimeException("Different number systems are used at the same time or an incorrect expression!");
            checkInputNum(a, b);
            SimpleCalc calc = calcFactory.createCalc();
            switch (numbers.get(1)) {
                case "+":
                    result = calc.plus(a, b);
                    break;
                case "-":
                    result = calc.minus(a, b);
                    break;
                case "*":
                    result = calc.multiply(a, b);
                    break;
                case "/":
                    result = calc.devide(a, b);
                    break;
            }
            System.out.println("calculation result = " + ((flagRoman) ? arabicInRoman(result) : result));
            numbers.clear();
            scannerStr.close();
        }
        scannerConsole.close();
    }

    //Проверка на число
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Проверка на число римских чисел
    public static boolean isNumericRomanNum(String str) {
        if (ROMAN_NUMBERS.containsValue(str))
            return true;
        else return false;
    }

    //Поиск ключа по значению в map
    public static int findKeyByValue(Map<Integer, String> map, String value) {
        int key = 0;
        for (Map.Entry<Integer, String> elem : map.entrySet()) {
            if (elem.getValue().equals(value)) {
                key = elem.getKey();
                break;
            }
        }
        return key;
    }

    //Перевод римских чисел в арабские
    public static String arabicInRoman(int num) {
        if (num < 1) throw new RuntimeException("Roman numbers cannot be less than one!");
        String temp;
        if (ROMAN_NUMBERS.containsKey(num)) temp = ROMAN_NUMBERS.get(num);
        else {
            temp = "X" + ((num < 20)? ROMAN_NUMBERS.get(num % 10): "X");
        }
        return temp;
    }

    //проверка соответствия значений условию
    public static boolean checkInputNum(int a, int b) {
        if ((a > 0 & a < 11) & (b > 0 & b < 11))
            return true;
        else throw new RuntimeException("The numbers do not fit the condition!");
    }

    //Фабрика посозданию калькуляторов
    public static CalcFactory createCalcByType(Enum type) {
        if (type.equals(TypeNum.ARABIC))
            return new ArabicCalcFactory();
        else if (type.equals(TypeNum.ROMAN))
            return new RomanCalcFactory();
        else throw new RuntimeException("Type calc not found!");
    }



    interface CalcFactory {
        public SimpleCalc createCalc();
    }

    static class ArabicCalcFactory implements CalcFactory {

        @Override
        public SimpleCalc createCalc() {
            return new ArabicCalc();
        }
    }

    static class RomanCalcFactory implements CalcFactory {

        @Override
        public SimpleCalc createCalc() {
            return new RomanCalc();
        }
    }

    interface SimpleCalc {
        int plus(int a, int b);

        int minus(int a, int b);

        int devide(int a, int b);

        int multiply(int a, int b);
    }

    static class ArabicCalc implements SimpleCalc {

        @Override
        public int plus(int a, int b) {
            return a + b;
        }

        @Override
        public int minus(int a, int b) {
            return a - b;
        }

        @Override
        public int devide(int a, int b) {
            if (b != 0)
                return (int) (a / b);
            else throw new ArithmeticException("cannot be divided by 0!");
        }

        @Override
        public int multiply(int a, int b) {
            return a * b;
        }
    }

    static class RomanCalc implements SimpleCalc {

        @Override
        public int plus(int a, int b) {
            return a + b;
        }

        @Override
        public int minus(int a, int b) {
            return a - b;
        }

        @Override
        public int devide(int a, int b) {
            if (b != 0)
                return (int) (a / b);
            else throw new ArithmeticException("cannot be divided by 0!");
        }

        @Override
        public int multiply(int a, int b) {
            return a * b;
        }
    }
}