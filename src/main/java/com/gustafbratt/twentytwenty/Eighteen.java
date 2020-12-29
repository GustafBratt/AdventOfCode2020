package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;

/* Part 1:
expressionList = value
               | value [operator value]*
value = number
      | ( expressionList )
 */
/* Part 2:
product = sum [+ sum]*

sum = value [* value]*

value = number
      | ( product )
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
        long totalSumP1 = 0;
        for(String line : lines) {
            thisLine = line.toCharArray();
            index = 0;
            lookAhead = thisLine[0];
            getNextToken();
            long i = productP2();
            System.out.println("Result: " + i);
            totalSumP1 += i;
        }
        System.out.println(totalSumP1);
    }

    //product = sum [+ sum]*
    private static long productP2() {
        long product = sumP2();
        while(thisChar == '*'){
            getNextToken('*');
            product *= sumP2();
        }
        return product;
    }

    //sum = value [* value]*
    private static long sumP2() {
        long sum = valueP2();
        while(thisChar == '+'){
            getNextToken('+');
            sum += valueP2();
        }
        return sum;
    }

    //value = number
    //      | ( expression )
    private static long valueP2() {
        if(thisChar=='('){
            getNextToken('(');
            long l = productP2();
            getNextToken(')');
            return l;
        }else{
            long val = Character.getNumericValue(thisChar);
            getNextNumber();
            return val;
        }
    }

    private static long parseExpressoionListP1() {
        char start = thisChar;
        long result = parseValueP1();
        while(true) {
            if (thisChar == 0 || thisChar == ')')
                return result;
            char operator = thisChar;
            getNextOperator();
            long val2 = parseValueP1();
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
        }
    }

    private static long parseValueP1() {
        if(thisChar=='('){
            getNextToken('(');
            long val = parseExpressoionListP1();
            getNextToken(')');
            return val;
        }else{
            long val = Character.getNumericValue(thisChar);
            getNextNumber();
            return val;
        }
    }
}
