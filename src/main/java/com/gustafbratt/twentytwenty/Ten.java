package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.math.BigInteger;
import java.util.*;

public class Ten {
    static long ones = 0;
    static long threes = 0;
    static List<Long> list;
    static Map<Integer, BigInteger> part2cache = new HashMap<>();
    public static void main(String[] args) {
        list = AoCUtils.fileAsLongs("10.txt");
        long jolts = 0;
        part1(list, jolts);
        threes++;
        System.out.println("ones " + ones);
        System.out.println("threes " + threes);
        System.out.println("Part1: " + (ones*threes));
        list.add(0L);
        Collections.sort(list);

        BigInteger paths = part2(0,0);
        System.out.println("Part 2: " + paths);
    }

    private static BigInteger part2(int index, int depth) {
        if(index == list.size()-1){
            return BigInteger.ONE;
        }
        if(part2cache.containsKey(index))
            return part2cache.get(index);
        List<Integer> children = new ArrayList<>(3);
        if(list.contains(list.get(index)+1))
            children.add(list.indexOf(list.get(index)+1));
        if(list.contains(list.get(index)+2))
            children.add(list.indexOf(list.get(index)+2));
        if(list.contains(list.get(index)+3))
            children.add(list.indexOf(list.get(index)+3));

        BigInteger sum = BigInteger.ZERO;
        for (Integer c : children) {
            sum = sum.add(part2(c, depth + 1));
        }
        part2cache.put(index, sum);
        return sum;
    }

    private static void part1(List<Long> list, long jolts) {
        while(true) {
            if (list.contains(jolts + 1)) {
                ones++;
                jolts++;
            } else if (list.contains(jolts + 2)) {
                jolts += 2;
            } else if (list.contains(jolts + 3)) {
                threes++;
                jolts += 3;
            }else{
                return;
            }
            System.out.println(jolts);
        }
    }
}
