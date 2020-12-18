package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Thirteen {
    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("13.txt");
        //part1(lines);
        String[] split = lines.get(1).split(",");
        List<Long> buses = new ArrayList<>();
        long largest = 0;
        int largestIndex = -1;
        for (int i = 0, splitLength = split.length; i < splitLength; i++) {
            String s = split[i];
            try {
                buses.add(Long.parseLong(s));
                if (Long.parseLong(s) > largest) {
                    largest = Long.parseLong(s);
                    largestIndex = i;
                }
            } catch (NumberFormatException e) {
                buses.add(null);
            }
        }
        System.out.println(largest);
        long time = -largestIndex;
        long iterations=0;
        while(true){
            time += largest;
            iterations++;
            if(iterations%1000000000==0)
                System.out.println(time);
            if(isMatchingMinute(buses, time)){
                System.out.println("Answer: ");
                System.out.println(time);
                System.exit(0);
            }
        }
    }

    private static boolean isMatchingMinute(List<Long> buses, long time) {
        for(int bus = 0; bus < buses.size(); bus++){
            if(buses.get(bus)!=null)
                if((time+bus)%buses.get(bus) !=0)
                    return false;
        }
        return true;
    }

    private static void part1(List<String> lines) {
        long start = Long.parseLong(lines.get(0));
        List<Long> buses = Arrays.stream(lines.get(1).split(","))
                .filter(s -> !"x".equals(s))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        long res = findBus(start, buses);
        System.out.println(res);
    }

    private static long findBus(long start, List<Long> buses) {
        int wait = 0;
        while (true) {
            for (Long bus : buses) {
                long diff = ((start+wait) % bus);
                if(diff==0)
                    return wait * bus;
            }
            wait++;
        }
    }
}
