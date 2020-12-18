package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Eleven {
    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("11.txt");
        Board board = new Board(lines);
        while(true) {
            Board next = board.copy();
            for(int row=0; row < board.array.length; row++){
                for(int col=0;  col < board.array[row].length; col++){
                    List<Character> adjacents = board.getAdajcentsPart2(row, col);
                    long occupied = adjacents.stream().filter(c -> c=='#').count();
                    if(next.array[row][col]=='L'){
                        if(occupied==0)
                            next.array[row][col] = '#';
                    }
                    if(next.array[row][col]=='#'){
                        if(occupied>4)
                            next.array[row][col] = 'L';
                    }
                }
            }
            board = next;
            System.out.println(board);
            System.out.println(board.count('#'));
        }
    }
}

class Board {
    //row, col
    char[][] array;
    public Board(List<String> input){
        array = new char[input.size()][];
        for(int i=0; i < input.size(); i++){
            array[i] = input.get(i).toCharArray();
        }
    }
    private Board(char[][] old){
        array = old;
    }
    public Board copy() {
        char[][] newarray = new char[array.length][];
        for(int i=0; i < array.length;i++) {
            newarray[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return new Board(newarray);
    }
    public List<Character> getAdajcentsPart2(int row, int col){
        List<Character> rsp = new ArrayList<>(8);
        rsp.add(adjacentN(row, col));
        rsp.add(adjacentS(row, col));
        rsp.add(adjacentE(row, col));
        rsp.add(adjacentW(row, col));
        rsp.add(adjacentNE(row, col));
        rsp.add(adjacentNW(row, col));
        rsp.add(adjacentSE(row, col));
        rsp.add(adjacentSW(row, col));
        return rsp;
    }

    private Character adjacentSW(int row, int col) {
        while(row<array.length-1 && col>0){
            row++;
            col--;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';
    }

    private Character adjacentSE(int row, int col) {
        while(row<array.length-1 && col<array[row].length-1){
            row++;
            col++;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';
    }

    private Character adjacentNW(int row, int col) {
        while(row>0 && col>0){
            row--;
            col--;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';
    }

    private Character adjacentNE(int row, int col) {
        while(row>0 && col<array[row].length-1){
            row--;
            col++;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';
    }

    private Character adjacentW(int row, int col) {
        while(col>0){
            col--;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';

    }

    private Character adjacentE(int row, int col) {
        while(col<array[row].length-1){
            col++;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';

    }

    private Character adjacentS(int row, int col) {
        while(row<array.length-1){
            row++;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';
    }

    private char adjacentN(int row, int col) {
        while(row>0){
            row--;
            if(array[row][col]!='.')
                return array[row][col];
        }
        return '.';
    }

    public List<Character> getAdjacentsPart1(int row, int col){
        List<Character> rsp = new ArrayList<>(8);
        try{rsp.add(array[row-1][col]);}catch (IndexOutOfBoundsException e){}
        try{rsp.add(array[row+1][col]);}catch (IndexOutOfBoundsException e){}
        try{rsp.add(array[row][col-1]);}catch (IndexOutOfBoundsException e){}
        try{rsp.add(array[row][col+1]);}catch (IndexOutOfBoundsException e){}
        try{rsp.add(array[row-1][col-1]);}catch (IndexOutOfBoundsException e){}
        try{rsp.add(array[row-1][col+1]);}catch (IndexOutOfBoundsException e){}
        try{rsp.add(array[row+1][col-1]);}catch (IndexOutOfBoundsException e){}
        try{rsp.add(array[row+1][col+1]);}catch (IndexOutOfBoundsException e){}
        return rsp;
    }

    @Override
    public String toString() {
            StringBuilder sb = new StringBuilder();
            for(int row=0; row<array.length; row++){
                sb.append(array[row]);
                sb.append("\n");
            }
            return sb.toString();
    }
    int count(char c) {
        int count = 0;
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                if (array[row][col] == c)
                    count++;
            }
        }
        return count;
    }
}
