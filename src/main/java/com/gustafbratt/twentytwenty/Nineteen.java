package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Nineteen {
    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("19.txt");
        for(String line : lines) {
            if("".equals(line))
                break;
            Rule rule = RuleFactory.makeRule(line);
            RuleDict.put(rule.getId(), rule);
        }
        for (Integer ruleId : RuleDict.keySet()) {
            System.out.println(RuleDict.get(ruleId));
        }
        int matches = 0;
        for(String input: lines) {
            if(input.contains(" ") || input.length()==0)
                continue;
            int consumed = RuleDict.get(0).consume(input);
            if (consumed == input.length()) {
                matches++;
                System.out.println(input + " ok");
            } else {
                System.out.println(input + " not ok");
            }
        }
        System.out.println("Matches: " + matches);
    }
}
class RuleDict{
    private static final Map<Integer, Rule> dict = new HashMap<>();
    public static Rule get(int i){
        return dict.get(i);
    }
    public static void put(int i, Rule r){
        dict.put(i, r);
    }
    public static Set<Integer> keySet() {
        return dict.keySet();
    }
}
interface Rule {
    int getId();
    int consume(String s);
}
class CompositeRule implements Rule {
    final private List<List<Integer>> rhs;
    final private int id;

    CompositeRule(int id, List<List<Integer>> rhs) {
        this.id = id;
        this.rhs = rhs;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int consume(String s) {
        int longest = 0;
        int possibleLists = 0;
        for(List<Integer> list : rhs){
            int totalConsumed = evaluateList(s,  list);
            if(totalConsumed!=0)
                possibleLists++;
            if(totalConsumed>longest)
                longest = totalConsumed;
        }
        if(possibleLists>1)
            System.out.println("There are more than one possible matches in rule " + id);
        return longest;
    }

    private int evaluateList(String s, List<Integer> list) {
        int totalConsumed = 0;
        for(Rule r : list.stream().map(RuleDict::get).collect(Collectors.toList())) {
            int consumed = r.consume(s.substring(totalConsumed));
            if(consumed == 0){
                return 0;
            }
            totalConsumed += consumed;
        }
        //System.out.println("Matching: " + s.substring(0,totalConsumed) + " " + getId() +": " + list);
        return totalConsumed;
    }

    @Override
    public String toString() {
        return id + ": " + rhs;
    }
}
class LeafRule implements Rule {
    private final char ch;
    private final int id;

    LeafRule(char ch, int id) {
        this.ch = ch;
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int consume(String s) {
        if(s.startsWith("" + ch))
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return id + ": \"" + ch + "\"";
    }

}


class RuleFactory {
    static Rule makeRule(String s){
        String[] rhsSplit = s.split(":");
        int id = Integer.parseInt(rhsSplit[0].trim());
        if(s.contains("\"")){
            return new LeafRule(s.charAt(s.length()-2), id);
        }
        List<List<Integer>> rhs = new ArrayList<>();
        for(String list : rhsSplit[1].split("\\|")){
            List<Integer> innerList = new ArrayList<>();
            for (String ruleStr : list.trim().split(" ")) {
                innerList.add(Integer.parseInt(ruleStr.trim()));
            }
            rhs.add(innerList);
        }
        return new CompositeRule(id, rhs);
    }
}
class NineteenHelper {
    public static void main(String[] args) {
        System.out.print("0: ");
        for(int i = 1; i < 10; i++){
            for(int j = 0 ; j < 10; j++ ){
                print8(i);
                print11(j+1);
                System.out.print("| ");
            }
        }
    }

    private static void print11(int j) {
        for(int ii = 0; ii < j; ii++){
            System.out.print("42 ");
        }
        for(int ii = 0; ii < j; ii++){
            System.out.print("31 ");
        }

    }

    private static void print8(int i) {
        for(int ii = 0; ii < i; ii++){
            System.out.print("42 ");
        }
    }
}