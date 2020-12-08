package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.*;

public class Seven {
    static Map<String, List<String>> bagRepository = new HashMap<>();
    static Map<String, Integer> part2Cache = new HashMap<>();
    public static void main(String[] args) {
        List<String> strings = AoCUtils.fileAsLines("7.txt");
        for(String s : strings){
            s = s.replaceAll("\\.", "");
            s = s.replaceAll("bags", "");
            s = s.replaceAll("bag", "");
            String[] split = s.split("  contain ");
            List<String> chilList = new ArrayList<>();
            bagRepository.put(split[0], chilList);
            HashMap<String, Integer> children = getChildren(split[1]);
            for(String childId : children.keySet()){
                for(int i = 0 ; i <  children.get(childId);i++){
                    chilList.add(childId);
                }
            }
        }
        System.out.println(bagRepository);
        /*int validOuters = 0;
        for(String outer : bagRepository.keySet()){
            System.out.println(outer);
            if(canHoldShinyGoldRecursevly(outer))
                validOuters++;
        }
        System.out.println("Part 1: " + validOuters);*/
        int shiny_gold = howManyRecursive("shiny gold");
        System.out.println(shiny_gold);
    }

    private static int howManyRecursive(String base) {
        if(part2Cache.containsKey(base))
            return part2Cache.get(base);
        int sum =  bagRepository.get(base).size();
        for(String child : bagRepository.get(base)){
            sum += howManyRecursive(child);
        }
        part2Cache.put(base, sum);
        return sum;
    }

    private static boolean canHoldShinyGoldRecursevly(String outer) {
        if(bagRepository.get(outer).contains("shiny gold"))
            return true;
        for(String child : bagRepository.get(outer)){
            if(canHoldShinyGoldRecursevly(child))
                return true;
        }
        return false;
    }

    private static HashMap<String, Integer> getChildren(String s) {
        HashMap<String, Integer> children = new HashMap<>();
        if(s.contains("no other"))
            return children;
        String[] split = s.split(" , ");
        for(String numbag : split){
            String[] s1 = numbag.split(" ");
            children.put(s1[1] + " " + s1[2], Integer.parseInt(s1[0]));
        }
        return children;
    }
}
