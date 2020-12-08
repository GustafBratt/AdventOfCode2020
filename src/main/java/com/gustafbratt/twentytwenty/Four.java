package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Four {
    public static final String[] REQ = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", };

    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("4.txt");
        List<Batch> batches = new ArrayList<>();
        Batch currentBatch = new Batch();
        for(String line: lines){
            currentBatch.addLine(line);
            if("".equals(line)){
                batches.add(currentBatch);
                currentBatch = new Batch();
            }
        }
        int valid = 0;
        for(Batch b : batches){
            if(b.hasAllFields(REQ))
                valid++;
        }
        System.out.println(valid);
        int valid2 = 0;
        for(Batch b : batches){
            if(b.isValidPart2())
                valid2++;
        }
        System.out.println(valid2);
    }
}

class Batch {
    Map<String, String> fields = new HashMap<>();

    @Override
    public String toString() {
        return "Batch{" +
                "fields=" + fields +
                '}';
    }

    public void addLine(String line){
        String[] pairs = line.split(" ");
        if("".equals(line))
            return;
        for(String pair: pairs){
            String[] splitPair = pair.split(":");
            fields.put(splitPair[0], splitPair[1]);
        }
    }
    public boolean hasAllFields(String[] req){
        for(String f : req){
            if(!fields.containsKey(f)) {
                return false;
            }
        }
        return true;
    }
    public boolean isValidPart2(){
        try {
            return hasAllFields(Four.REQ) && validBYR() && validIYR() && validEYR() && validHGT() &&
                    validHCL() && validECL() && validPID();
        }catch (Exception e){
            return false;
        }
    }

    private boolean validPID() {
        String rep = fields.get("pid").replaceFirst("\\d\\d\\d\\d\\d\\d\\d\\d\\d", "");
        return "".equals(rep);
    }

    private boolean validECL() {
        String[] ok = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth",};
        return Arrays.asList(ok).contains(fields.get("ecl"));
    }

    private boolean validHCL() {
        Predicate<String> pat = Pattern.compile("#([a-f0-9]{6})").asMatchPredicate();
        return pat.test(fields.get("hcl"));

    }

    private boolean validHGT() {
        String field = fields.get("hgt");
        if(field.contains("cm")){
            int hgt = Integer.parseInt(field.substring(0,field.length()-2));
            return hgt >= 150 && hgt <= 193;
        }
        if(field.contains("in")){
            int hgt = Integer.parseInt(field.substring(0,field.length()-2));
            return hgt >= 59 && hgt <= 76;
        }
        return false;
    }

    private boolean validEYR() {
        int eyr;
        try {
            eyr = Integer.parseInt(fields.get("eyr"));
        }catch(Exception e){
            return false;
        }
        return eyr >= 2020 && eyr <= 2030;
    }

    private boolean validIYR() {
        int iyr;
        try {
            iyr = Integer.parseInt(fields.get("iyr"));
        }catch(Exception e){
            return false;
        }
        return iyr >= 2010 && iyr <= 2020;
    }

    private boolean validBYR() {
        int byr;
        try {
            byr = Integer.parseInt(fields.get("byr"));
        }catch(Exception e){
            return false;
        }
        return byr >= 1920 && byr <= 2002;
    }
}