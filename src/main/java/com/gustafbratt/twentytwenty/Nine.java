package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;

public class Nine {
    private static final Integer LENGTH = 25;
    static List<Long> list = AoCUtils.fileAsLongs("9.txt");
    private static final long part1Sum = 2089807806;
    //private static final long part1Sum = 127;


    public static void main(String[] args) {
        for(int i = 0; i < list.size()-1; i++){
            for(int j = i+1; j < list.size(); j++){
                //System.out.println(i + " " + j);
                long sum = getSum(i,j);
                if(sum==part1Sum) {
                    long minmax = getMinMax(i,j);
                    System.out.println(list.get(i) + " -> " + list.get(j-1));
                    System.out.println(minmax);
                }
            }
        }
    }

    private static long getMinMax(int start, int end) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for(int i=start; i < end; i++){
            if(list.get(i)<min)
                min = list.get(i);
            if(list.get(i)>max)
                max = list.get(i);
        }
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
        return min+max;
    }

    private static long getSum(int start, int end) {
        long sum = 0;
        for(int i = start; i < end; i++){
            sum += list.get(i);
        }
        return sum;
    }

    static void part1() {
        for(int i=LENGTH; i < list.size(); i++ ) {
            System.out.print(list.get(i) + " ");
            boolean isSum = isSum(list.get(i), i-LENGTH, i-1);
            System.out.println(" " + isSum);
        }
    }

    private static boolean isSum(Long sum, int start, int end) {
        System.out.print(" [" + list.get(start) + ", " + list.get(end) + "]");
        for(int i=start; i < end+1; i++){
            for(int j = start; j < end+1; j++){
                if(i!=j && list.get(i)+list.get(j)==sum)
                    return true;
            }
        }
        return false;
    }
}
