package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.security.cert.CollectionCertStoreParameters;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Five {
    public static void main(String[] args) {
        List<String> strings = AoCUtils.fileAsLines(5);
        OptionalInt max = strings.stream().mapToInt(Five::getId).max();
        System.out.println("Part 1: " + max.orElse(-1));
        List<Integer> allSeats = strings.stream().mapToInt(Five::getId).sorted().boxed().collect(Collectors.toList());
        for(int i : allSeats){
            if(!allSeats.contains(i+1))
                System.out.println("part 2: " +  (i+1));
        }
    }
    static int getId(String seat) {
        String line = seat.substring(0,7);
        line = line.replaceAll("F", "0").replaceAll("B", "1");
        int lineInt = Integer.parseInt(line, 2);
        String row = seat.substring(7,10);
        row = row.replaceAll("R", "1").replaceAll("L", "0");
        int rowInt = Integer.parseInt(row, 2);
        return lineInt*8+rowInt;
    }
}
