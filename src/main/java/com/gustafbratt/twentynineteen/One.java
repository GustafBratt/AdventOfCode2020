package com.gustafbratt.twentynineteen;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;

public class One {
    public static void main(String[] args) {
        List<Integer> lines = AoCUtils.fileAsInts("2019_1.txt");
        int total = 0;
        total = lines.stream().map(l -> (l/3)-2).mapToInt(i->i).sum();
        System.out.println(total);
    }
}
