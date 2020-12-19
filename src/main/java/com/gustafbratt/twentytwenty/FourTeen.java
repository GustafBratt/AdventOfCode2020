package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourTeen {
    static Map<Long, Long> memory = new HashMap<>();
    public static void main(String[] args) {
        String mask = "";
        List<String> lines = AoCUtils.fileAsLines("14.txt");
        part1(lines);
        memory.clear();
        for(String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.substring(7, line.length());
            } else {
                int value = Integer.parseInt(line.split(" = ")[1]);
                String addressStr = line.split(" = ")[0]
                        .replaceAll("mem\\[", "")
                        .replaceAll("\\]", "");
                String address = applyBits(Integer.parseInt(addressStr), mask);
                List<Long> memoryRange = getMemoryRange(address);
                for(Long addr : memoryRange) {
                    memory.put(addr, (long)value);
                }
            }
        }
        BigInteger sum = BigInteger.ZERO;
        for(long val : memory.values()){
            sum = sum.add(BigInteger.valueOf(val));
        }
        System.out.println("Part2: " + sum);

    }
    static List<Long> getMemoryRange(String input) {
        List<Long> result = new ArrayList<>();
        long xCount = input.chars().filter(ch -> ch == 'X').count();
        for(int i = 0; i < Math.pow(2, xCount); i++){
            char[] inputAsChars = input.toCharArray();
            int replaced = 0;
            String intStr = String.format("%" + xCount + "s", Integer.toBinaryString(i)).replace(" ", "0");
            for(int j=0; j < input.length(); j++) {
                if(inputAsChars[j]=='X') {
                    inputAsChars[j] = intStr.charAt(replaced);
                    replaced++;
                }
            }
            result.add(Long.parseLong(new String(inputAsChars), 2));
        }
        return result;
    }

    private static String applyBits(int mem, String mask) {
        String result = "";
        String addrStr = String.format("%36s", Integer.toBinaryString(mem)).replace(' ', '0');
        for(int i = 0; i < addrStr.length(); i++) {
            char ch = addrStr.charAt(i);
            if(mask.charAt(i)=='X' || mask.charAt(i)=='1')
                ch = mask.charAt(i);
            result = result + ch;
        }
        return result;
    }

    private static void part1(List<String> lines) {
        long maskZeroes = 0;
        long maskOnes = 0;
        for(String line : lines){
            if(line.startsWith("mask")){
                maskZeroes = getMaskZeroes(line);
                maskOnes = getMaskOnes(line);
            }else{
                long num = Long.parseLong(line.split(" = ")[1]);
                num = num | maskOnes;
                num = num & maskZeroes;
                String addressStr = line.split(" = ")[0]
                        .replaceAll("mem\\[", "")
                        .replaceAll("\\]", "");
                memory.put(Long.parseLong(addressStr), num);
            }
        }
        BigInteger sum = BigInteger.ZERO;
        for(long val : memory.values()){
            sum = sum.add(BigInteger.valueOf(val));
        }
        System.out.println("Part1: " + sum);
    }

    private static long getMaskOnes(String line) {
        String mask = line.substring(7, line.length()).replaceAll("X", "0");
        return Long.parseLong(mask, 2);
    }

    private static long getMaskZeroes(String line) {
        String mask = line.substring(7, line.length()).replaceAll("X", "1");
        return Long.parseLong(mask, 2);
    }
}
