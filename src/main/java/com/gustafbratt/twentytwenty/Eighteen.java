package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;

/*
expressionList = value
               | value operator expressionList
value = number
      | ( expressionList )
 */
public class Eighteen {
    static char thisChar;
    static char lookAhead;

    static char[] thisLine;
    static int index;
    static void getNextToken() {
        thisChar = lookAhead;
        index++;
        try {
            if (thisLine[index] == ' ') {
                index++;
            }
            lookAhead = thisLine[index];
        }catch (IndexOutOfBoundsException e){
            lookAhead = 0;
        }
    }
    static void getNextToken(char ch){
        if(ch!=thisChar)
            throw new RuntimeException("Expected " + ch + " but found " + thisChar);
        getNextToken();
    }
    static void getNextOperator() {
        if(thisChar!='*' && thisChar!='+')
            throw new RuntimeException("Expected operator, found " + thisChar);
        getNextToken();
    }
    static void getNextNumber() {
        if(!(thisChar >= '0' && thisChar<='9'))
            throw new RuntimeException("Epxected number, found " + thisChar);
        getNextToken();
    }

    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("18.txt");
        long totalSum = 0;
        for(String line : lines) {
            thisLine = new StringBuilder(line)
                    .reverse()
                    .toString()
                    .replaceAll("\\(", "p")
                    .replaceAll("\\)", "(")
                    .replaceAll("p", ")")
                    .toCharArray();
            index = 0;
            lookAhead = thisLine[0];
            getNextToken();
            long i = expressoionList();
            System.out.println("Result: " + i);
            totalSum += i;
        }
        System.out.println(totalSum);
    }

    private static long expressoionList() {
        char start = thisChar;
        long result = parseValue();
        if (thisChar == 0 || thisChar == ')')
            return result;
        char operator = thisChar;
        getNextOperator();
        long val2 = expressoionList();
        switch (operator) {
            case '+' -> {
                System.out.println(result + " + " + val2);
                result = result + val2;
            }
            case '*' -> {
                System.out.println(result + " * " + val2);
                result = result * val2;
            }
            default -> throw new RuntimeException("Operator: " + operator);
        }
        return result;
    }

    private static long parseValue() {
        if(thisChar=='('){
            getNextToken('(');
            long val = expressoionList();
            getNextToken(')');
            return val;
        }else{
            long val = Character.getNumericValue(thisChar);
            getNextNumber();
            return val;
        }
    }
}
