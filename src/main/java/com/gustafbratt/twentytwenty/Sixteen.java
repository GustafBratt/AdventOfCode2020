package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Sixteen {
    static List<String> lines = AoCUtils.fileAsLines("16.txt");

    public static void main(String[] args) {
        List<Interval> allIntervals = getIntervals();
        List<String> allTickets = getTickets();
        List<String> okLines = new ArrayList<>();
        String myTicket = getMyTicket();
        long errorRate = 0;
        for (String ticket : allTickets) {
            List<Integer> numbers = Arrays.stream(ticket.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            errorRate += numbers
                    .stream()
                    .filter(value -> allIntervals.stream().noneMatch(interval -> interval.test(value)))
                    .mapToLong(value -> value).sum();

        }
        System.out.println("Part 1: " + errorRate);



        for(String ticket : allTickets){
            List<Integer> numbers = Arrays.stream(ticket.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            if(numbers.stream().mapToInt(value -> value).noneMatch(value -> allIntervals.stream().noneMatch(i -> i.test(value)))){
                okLines.add(ticket);
            }
        }

        okLines.add(myTicket);
        Map<Integer, Set<Interval>> matches = new HashMap<>();
        for(int col = 0 ; col < 20; col++) {
            for (Interval interval : allIntervals) {
                List<Integer> column = getColumn(col, okLines);
                boolean allLinesMatch = true;
                for (int value : column) {
                    if (!interval.test(value)) {
                        allLinesMatch = false;
                    }
                }
                if(allLinesMatch)
                    matches.computeIfAbsent(col,k->new HashSet<>()).add(interval);
            }
        }
        System.out.println(matches);
        List<Interval> reduntants = new ArrayList<>();
        for(int k = 0; k < 20; k++){
            for (int col : matches.keySet()) {
                System.out.println(col + ": " + matches.get(col).stream().map(i -> i.name).collect(Collectors.toList()));
                if (matches.get(col).size() == 1) {
                    reduntants.add(matches.get(col).iterator().next());
                }
            }
            System.out.println("Removing all " + reduntants);
            for (int col : matches.keySet()) {
                if (matches.get(col).size() != 1) {
                    for(Interval reduntant : reduntants)
                        matches.get(col).remove(reduntant);
                }
            }
        }
        System.out.println("============");
        long result = 1;
        for(int col = 0; col < 20; col++){
            if(matches.get(col).iterator().next().name.startsWith("departure")){
                List<Integer> numbers = Arrays.stream(myTicket.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                result *= numbers.get(col);
            }
        }
        System.out.println(result);
    }

    private static String getMyTicket() {
        for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
            String line = lines.get(i);
            if("your ticket:".equals(line)){
                return lines.get(i+1);
            }
        }
        throw new IllegalArgumentException();
    }
    private static List<Integer> getColumn(int i, List<String> okLines) {
        return okLines.stream().map(s -> s.split(",")[i]).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    }


    private static List<String> getTickets() {
        List<String> result = new ArrayList<>();
        boolean tickets = false;
        for(String line : lines) {
            if(tickets) {
                result.add(line);
            }
            if(line.equals("nearby tickets:"))
                tickets = true;
        }
        return result;
    }


    private static List<Interval> getIntervals() {
        List<Interval> result = new ArrayList<>();
        for(String line : lines){
            if("".equals(line))
                return result;
            Pattern r = Pattern.compile("(.*): (\\d+)-(\\d+) or (\\d+)-(\\d+)");
            Matcher matcher = r.matcher(line);
            matcher.find();
            Interval interval = new Interval(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
            result.add(interval);
        }
        return result;
    }
}
