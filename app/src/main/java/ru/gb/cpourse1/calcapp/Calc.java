package ru.gb.cpourse1.calcapp;

import java.util.Scanner;

public class Calc {
    private double sum;
    private double currentNumber;

    private static final char SPACE_CHAR = ' ';

    private static final char ADD_OPERATION_CHAR = '+';
    private static final char SUB_OPERATION_CHAR = '-';
    private static final char MUL_OPERATION_CHAR = 'X';
    private static final char DIV_OPERATION_CHAR = '/';
    private static final char DECIMAL_DOT_CHAR = '.';

    public static char getAddOperationChar() {
        return ADD_OPERATION_CHAR;
    }

    public static char getSubOperationChar() {
        return SUB_OPERATION_CHAR;
    }

    public static char getMulOperationChar() {
        return MUL_OPERATION_CHAR;
    }

    public static char getDivOperationChar() {
        return DIV_OPERATION_CHAR;
    }

    public static char getDecimalDotChar() {
        return DECIMAL_DOT_CHAR;
    }

    public Calc() {}


    public double calculate(StringBuilder expressionStringBuilder) {
        sum = 0;

        if (isStringBuilderEmpty(expressionStringBuilder)) return 0d;

        expressionStringBuilder = insertSpacesBetweenNumbersAndOperations(expressionStringBuilder);
        Scanner scanner = new Scanner(expressionStringBuilder.toString());

        currentNumber = scanner.nextDouble();
        while (scanner.hasNext()) {
            switch (scanner.next().charAt(0)) {
                case ADD_OPERATION_CHAR:
                    sum += currentNumber;
                    currentNumber = scanner.nextDouble();
                    break;
                case SUB_OPERATION_CHAR:
                    sum += currentNumber;
                    currentNumber = scanner.nextDouble() * -1;
                    break;
                case MUL_OPERATION_CHAR:
                    currentNumber *= scanner.nextDouble();
                    break;
                case DIV_OPERATION_CHAR:
                        currentNumber /= scanner.nextDouble();
                    break;
            }
        }
        scanner.close();
        sum += currentNumber;
        return sum;
    }

    private boolean isStringBuilderEmpty (StringBuilder testedString) {
        return testedString.toString().isEmpty();
    }

    private StringBuilder insertSpacesBetweenNumbersAndOperations(StringBuilder expressionStringBuilder) {
        for (int i = 0; i < expressionStringBuilder.length(); i++) {
            switch (expressionStringBuilder.charAt(i)) {
                case ADD_OPERATION_CHAR:
                case SUB_OPERATION_CHAR:
                    if (i==0) break;
                    else if(expressionStringBuilder.charAt(i-1) == SPACE_CHAR) break;
                case MUL_OPERATION_CHAR:
                case DIV_OPERATION_CHAR:
                    expressionStringBuilder.insert(i, SPACE_CHAR);
                    expressionStringBuilder.insert(i + 2, SPACE_CHAR);
                    i += 2;
                    break;
            }
        }
        return expressionStringBuilder;
    }

}
