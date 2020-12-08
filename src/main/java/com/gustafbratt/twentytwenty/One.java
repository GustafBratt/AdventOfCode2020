package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;

public class One {
    public static void main(String[] args) {
        List<Integer> list = AoCUtils.fileAsInts("1.txt");
        //int result = getit(list);
        //System.out.println(result);
        int result2 = part2(list);
        System.out.println(result2);
    }

    private static int part2(List<Integer> list) {
        for(Integer i : list){
            for(Integer j : list){
                if(list.contains(2020-(i+j)))
                    return i*j*(2020-(i+j));
            }
        }
        return -1;
    }

    private static int getit(List<Integer> list) {
        for(Integer i : list){
            if(list.contains(2020-i))
                return i * (2020-i);
        }
        return -1;
    }
}
