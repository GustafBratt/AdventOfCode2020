package com.gustafbratt.twentytwenty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Interval implements Predicate<Integer> {
    String name;
    int low1;
    int high1;
    int low2;
    int high2;
    //public List<Integer> possibleFields = IntStream.rangeClosed(0, 20)
    //        .boxed().collect(Collectors.toList());

    //public Set<Integer> failFields = new HashSet<>();

    public Interval(String name, String low1, String high1, String low2, String high2) {
        this.name = name;
        this.low1 = Integer.parseInt(low1);
        this.high1 = Integer.parseInt(high1);
        this.low2 = Integer.parseInt(low2);
        this.high2 = Integer.parseInt(high2);
    }

    //True if within interval
    @Override
    public boolean test(Integer integer) {
        return (integer >= low1 && integer <= high1) ||
                (integer >= low2 && integer <= high2);
    }
}
