package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Eight {

    public static final String FILENAME = "8.txt";

    public static void main(String[] args) {
        List<String> lines;
        lines = AoCUtils.fileAsLines(FILENAME);
        for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
            String l = lines.get(i);
            if (l.startsWith("jmp")) {
                List<String> modifiedLines = modifyLines(i);
                int acc = getAcc(modifiedLines);
                if(acc!=-1)
                    System.out.println("Part 2: " + acc);

            }
        }
    }

    private static List<String> modifyLines(int i) {
        List<String> lines = AoCUtils.fileAsLines(FILENAME);
        String s = lines.get(i).replaceAll("jmp", "nop");
        lines.set(i, s);
        return lines;
    }

    private static int getAcc(List<String> lines) {
        Set<Integer> visited = new HashSet<>();
        int pc = 0;
        int acc = 0;
        while(pc<lines.size()){
            String line = lines.get(pc);
            if(visited.contains(pc))
                return -1;
            visited.add(pc);
            String[] split = line.split(" ");
            int arg = Integer.parseInt(split[1]);
            switch (split[0]){
                case "nop":
                    pc++;
                    break;
                case "acc":
                    acc += arg;
                    pc++;
                    break;
                case "jmp":
                    pc += arg;
                    break;
                default:
                    throw new IllegalArgumentException(line);
            }
        }
        return acc;
    }
}
