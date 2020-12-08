package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;


//Fel svar: 90209024
public class Three {
    static List<String> lines;
    public static void main(String[] args) {
        lines = AoCUtils.fileAsLines("3.txt");
        int p2 = findTrees(1,1);
        System.out.println(p2);
        int part1 = findTrees(1,3);
        System.out.println(part1);
        int p3 = findTrees(1,5);
        System.out.println(p3);
        int p4 = findTrees(1,7);
        System.out.println(p4);
        long p5 = findTrees(2,1);
        System.out.println(p5);
        System.out.println("Part2: " + part1*p2*p3*p4*p5);
        
        cheat();
    }

    private static void cheat() {
        int a = part2(lines.toArray(new String[0]), 1,1);
        int b = part2(lines.toArray(new String[0]), 3,1);
        int c = part2(lines.toArray(new String[0]), 5,1);
        int d = part2(lines.toArray(new String[0]), 7,1);
        int e = part2(lines.toArray(new String[0]), 1,2);
        System.out.println("Cheat: " + a*b*c*d*e);
    }

    private static int findTrees(int down, int right) {
        int trees = 0;
        int x = 0;
        for (int i = 0, linesSize = lines.size(); i < linesSize; i+=down) {
            String line = lines.get(i);
            if (line.charAt(x % (line.length())) == '#')
                trees++;
            x += right;
        }
        return trees;
    }

    public static int part2(String[] input, int slopeX, int slopeY){
        int trees = 0;
        int rightPos = 0;
        for(int i = 0; i < input.length; i+=slopeY){
            if(input[i].charAt(rightPos) == '#'){
                trees++;
            }
            rightPos = (rightPos + slopeX)%input[i].length();
        }
        return trees;
    }
}
