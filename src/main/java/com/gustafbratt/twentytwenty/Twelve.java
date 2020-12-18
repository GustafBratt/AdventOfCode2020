package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;

public class Twelve {
    static int row = 0;
    static int col = 0;
    static int rowWaypointRelative = -1;
    static int colWaypointRelative = 10;
    static int direction = 1; //0,1,2,3 -> N, E, S, W
    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("12.txt");
        System.out.print("col: " + col + " row: " + row + " dir: " + direction);
        System.out.println(" [ " + colWaypointRelative + ", " + rowWaypointRelative + "]");

        for(String line : lines){
            char cmd = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));
            //part1(cmd, value);
            part2(cmd, value);
            System.out.println(line);
            System.out.print("col: " + col + " row: " + row + " dir: " + direction);
            System.out.println(" [ " + colWaypointRelative + ", " + rowWaypointRelative + "]");

        }
        System.out.println("row: " + row + " col: " + col);
        System.out.println("Distance: " + (Math.abs(row) + Math.abs(col)));
    }

    private static void part2(char cmd, int value) {
        switch (cmd){
            case 'N' -> moveWaypoint(Direction.NORTH, value);
            case 'S' -> moveWaypoint(Direction.SOUTH, value);
            case 'E' -> moveWaypoint(Direction.EAST, value);
            case 'W' -> moveWaypoint(Direction.WEST, value);
            case 'L' -> rotateWaypointL(value);
            case 'R' -> rotateWaypointR(value);
            case 'F' -> forwardWaypoint(value);
            default -> throw new IllegalStateException("Unexpected value: " + cmd);
        }
    }

    private static void forwardWaypoint(int value) {
        int rowDiff = rowWaypointRelative;
        int colDiff = colWaypointRelative;
        row += (rowDiff * value);
        col += (colDiff * value);
    }

    private static void rotateWaypointR(int value) {
        int quads = value/90;
        for(int i = 0; i < quads; i++){
            int newrow = colWaypointRelative;
            colWaypointRelative = -rowWaypointRelative;
            rowWaypointRelative = newrow;
        }
    }
    private static void rotateWaypointL(int value) {
        int quads = value/90;
        for(int i = 0; i < quads; i++){
            int newrow = -colWaypointRelative;
            colWaypointRelative = rowWaypointRelative;
            rowWaypointRelative = newrow;
        }
    }

    private static void moveWaypoint(Direction dir, int length) {
        rowWaypointRelative += dir.getRow()*length;
        colWaypointRelative += dir.getCol()*length;

    }

    private static void part1(char cmd, int value) {
        switch (cmd) {
            case 'N' -> move(Direction.NORTH, value);
            case 'S' -> move(Direction.SOUTH, value);
            case 'E' -> move(Direction.EAST, value);
            case 'W' -> move(Direction.WEST, value);
            case 'L', 'R' -> rotate(cmd, value);
            case 'F' -> forward(value);
        }
    }

    private static void forward(int value) {
        switch ((direction+4)%4) {
            case 0:
                row -= value;
                break;
            case 1:
                col += value;
                break;
            case 2:
                row += value;
                break;
            case 3:
                col -= value;
                break;
            default:
                throw new IllegalArgumentException("Direction is " + direction);
        }
    }

    private static void rotate(char cmd, int value) {
        if(cmd=='L')
            value = -value;
        direction += (value/90);
    }

    private static void move(Direction dir, int length) {
        row += dir.getRow()*length;
        col += dir.getCol()*length;
    }
}
enum Direction{
    NORTH(-1,0),
    SOUTH(1,0),
    EAST(0,1),
    WEST(0,-1);
    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    Direction(int row, int col){
        this.row = row;
        this.col = col;
    }
}