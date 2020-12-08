package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.List;

public class Two {
    public static void main(String[] args) {
        int step1 = 0;
        int step2 = 0 ;
        List<String> lines = AoCUtils.fileAsLines("2.txt");
        for(String line : lines){
            String[] lineArr = line.split(" ");
            String nums = lineArr[0];
            String tecken = lineArr[1].replace(":","");
            String pwd = lineArr[2];
            int low = Integer.parseInt(nums.split("-")[0]);
            int high = Integer.parseInt(nums.split("-")[1]);
            int count = countSubstrings(pwd, tecken);
            if(count >= low && count <= high)
                step1++;
            if(pwd.charAt(low-1)==tecken.charAt(0) ^ pwd.charAt(high-1)==tecken.charAt(0)) {
                System.out.println(pwd);
                step2++;
            }
        }
        System.out.println(step1);
        System.out.println(step2);
    }

    public static int countSubstrings(String str, String findStr){
        return str.split(findStr, -1).length-1;
    }
}
