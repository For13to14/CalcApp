package ru.gb.cpourse1.calcapp;

import java.util.Scanner;

public class Calc {
    private static double sum;
    private static double currentNumber;


    public static double calculate(StringBuilder expressionStringBuilder) {
        sum = 0;

        if (isStringBuilderEmpty(expressionStringBuilder)) return 0d;

        expressionStringBuilder = insertSpacesBetweenNumbersAndOperations(expressionStringBuilder);
        Scanner scanner = new Scanner(expressionStringBuilder.toString());

        currentNumber = scanner.nextDouble();
        while (scanner.hasNext()) {
            switch (scanner.next()) {
                case "+":
                    sum += currentNumber;
                    currentNumber = scanner.nextDouble();
                    break;
                case "-":
                    sum += currentNumber;
                    currentNumber = scanner.nextDouble() * -1;
                    break;
                case "X":
                    currentNumber *= scanner.nextDouble();
                    break;
                case "/":
                    //
                        currentNumber /= scanner.nextDouble();
                   // } catch ()
                    break;
            }
        }
        scanner.close();
        sum += currentNumber;
        return sum;
    }

    private static boolean isStringBuilderEmpty (StringBuilder testedString) {
        return testedString.toString().isEmpty();
    }

    private static StringBuilder insertSpacesBetweenNumbersAndOperations(StringBuilder expressionStringBuilder) {
        for (int i = 0; i < expressionStringBuilder.length(); i++) {
            switch (expressionStringBuilder.charAt(i)) {
                case '+':
                case '-':
                    if (expressionStringBuilder.charAt(i-1) == ' ' || i==0) break;
                case 'X':
                case '/':
                    expressionStringBuilder.insert(i, ' ');
                    expressionStringBuilder.insert(i + 2, ' ');
                    i += 2;
            }
        }
        return expressionStringBuilder;
    }

}
