package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.*;

public class Six {
    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("6.txt");
        Set<Character> batch = new HashSet<>();
        long sum = 0;
        for(String l : lines){
            if("".equals(l)) {
                sum += batch.size();
                batch.clear();
            }
            for(char c : l.toCharArray())
                batch.add(c);
        }
        System.out.println("Part 1: " + sum);
        doPart2(lines);
    }

    private static void doPart2(List<String> lines) {
        Group g = new Group();
        int sum = 0;
        for (String l : lines) {
            if ("".equals(l)) {
                sum += g.getSum();
                g = new Group();
            }
            g.addLine(l);
        }
        System.out.println("Part 2: " + sum);
    }
}

class Group{
    List<String> list = new ArrayList<>();
    public void addLine(String l) {
        if("".equals(l))
            return;
        list.add(l);
    }

    public int getSum() {
        char[] carArr = list.get(0).toCharArray();
        int sum = 0;
        for(Character c : carArr){
            boolean inAll = true;
            for(String s : list){
                if(s.indexOf(c)==-1){
                    inAll = false;
                }
            }
            if(inAll)
                sum++;
        }
        System.out.println(sum);
        return sum;
    }
}