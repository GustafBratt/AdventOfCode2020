package com.gustafbratt.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class AoCUtils {
    public static List<String> fileAsLines(String filename){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());
    }

    public static List<Integer> fileAsInts(String filename){
        return fileAsLines(filename).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
    public static List<Integer> fileAsInts(int fileNumber){
        return fileAsInts("" + fileNumber +".txt");
    }
    public static List<String> fileAsLines(int fileNumber){
        return fileAsLines("" + fileNumber +".txt");
    }

    public static List<Long> fileAsLongs(String filename){
        return fileAsLines(filename).stream().map(Long::parseLong).collect(Collectors.toList());
    }

}

