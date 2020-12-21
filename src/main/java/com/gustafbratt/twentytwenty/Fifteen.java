package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fifteen {
    static Map<Long, Long> lastMentioned = new HashMap<>();  // Number -> last mentioned
    final static long TARGET = 30000000;
    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("15.txt");
        String[] split = lines.get(0).split(",");
        for(int i = 0; i< split.length-1; i++) {
            lastMentioned.put(Long.parseLong(split[i]), i+1L);
        }
        long lastSpoken = Long.parseLong(split[split.length-1]);
        //Round 4:
        for(long round = split.length+1; round < TARGET+1; round++) {
            if(round%3000000==0)
                System.out.println(round);
            Long tmp = -1L;
            if (!lastMentioned.containsKey(lastSpoken)) {
                lastMentioned.put(lastSpoken, round - 1);
                lastSpoken = 0;
            } else {
                long nextNumber = round-1 - lastMentioned.get(lastSpoken);
                lastMentioned.put(lastSpoken, round - 1);
                lastSpoken = nextNumber;
            }
            //System.out.println("Round " + round + " Spoken: " + lastSpoken);

        }
        System.out.println("Ans: " + lastSpoken);

    }
}
