package com.gustafbratt.twentytwenty;

import com.gustafbratt.utils.AoCUtils;

import java.util.*;
import java.util.function.ToIntFunction;

public class Seventeen {
    public static void main(String[] args) {
        List<String> lines = AoCUtils.fileAsLines("17.txt");
        Space oldSpace = new Space();
        for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
            String line = lines.get(i);
            char[] charArray = line.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                char ch = charArray[j];
                oldSpace.set(new Point(i,j,0, 0), ch);
            }
        }

        final int ITERATIONS = 6;
        for(int i = 0; i < 6; i++) {
            Space newSpace = new Space();
            for(int x = oldSpace.getMin(Point::getX)-1; x < oldSpace.getMax(Point::getX)+2; x++) {
                for (int y = oldSpace.getMin(Point::getY) - 1; y < oldSpace.getMax(Point::getY) + 2; y++) {
                    for (int z = oldSpace.getMin(Point::getZ) - 1; z < oldSpace.getMax(Point::getZ) + 2; z++) {
                        for (int w = oldSpace.getMin(Point::getW) - 1; w < oldSpace.getMax(Point::getW) + 2; w++) {
                            int activeCount = 0;
                            Point thisPoint = new Point(x, y, z, w);
                            for (Point adj : thisPoint.getAdjacents()) {
                                if (oldSpace.get(adj) == '#')
                                    activeCount++;
                            }
                            if (oldSpace.get(thisPoint) == '#') {
                                if (activeCount == 2 || activeCount == 3) {
                                    newSpace.set(thisPoint, '#');
                                } else {
                                    newSpace.set(thisPoint, '.');
                                }
                            } else {//thisPoint is .
                                if (activeCount == 3) {
                                    newSpace.set(thisPoint, '#');
                                }
                            }
                        }
                    }
                }
            }
            //oldSpace.printZplane(0);
            System.out.println(i + ": " + newSpace.activeStateCount());
            oldSpace = newSpace;

        }
    }
}
class Space {
    private final Map<Point, Character> chars = new HashMap<>();
    void set(Point p, char ch) {
        chars.put(p, ch);
    }
    char get(Point p){
        if(chars.containsKey(p))
            return chars.get(p);
        return '.';
    }
    char get(int x, int y, int z, int w){
        return get(new Point(x, y, z, w));
    }

    int getMax(ToIntFunction<Point> key){
        return key.applyAsInt(chars
                .keySet()
                .stream()
                .max(Comparator.comparingInt(key))
                .orElse(Point.ZERO)
        );
    }
    int getMin(ToIntFunction<Point> key){
        return key.applyAsInt(chars
                .keySet()
                .stream()
                .min(Comparator.comparingInt(key))
                .orElse(Point.ZERO)
        );
    }

    long activeStateCount() {
        return chars.values().stream().filter(ch -> ch=='#').count();
    }

    /*public void printZplane(int z) {
        for (int x = getMin(Point::getX); x < getMax(Point::getX)+1; x++) {
            for (int y = getMin(Point::getY); y < getMax(Point::getY)+1; y++) {
                System.out.print(get(x, y, z, 0));
            }
            System.out.println("");
        }
    } */
}
class Point {
    private final int x;
    private final int y;
    private final int z;
    private final int w;

    static Point ZERO = new Point(0,0,0, 0);

    public Point(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z +  ", " + w +")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getW() {
        return w;
    }

    public List<Point> getAdjacents() {
        List<Point> result = new ArrayList<>();
        result.addAll(getSpaceAdjacents(w-1));
        result.addAll(getSpaceAdjacents(w));
        result.addAll(getSpaceAdjacents(w+1));
        result.remove(new Point(x,y,z,w));
        return result;
    }
    private List<Point> getSpaceAdjacents(int w) {
        return List.of(
                new Point(x+1,y+1,z+1, w),
                new Point(x+1,y+1,z, w),
                new Point(x+1,y+1,z-1, w),

                new Point(x+1,y,z+1, w),
                new Point(x+1,y,z, w),
                new Point(x+1,y,z-1, w),

                new Point(x+1,y-1,z+1, w),
                new Point(x+1,y-1,z, w),
                new Point(x+1,y-1,z-1, w),
                //
                new Point(x,y+1,z+1, w),
                new Point(x,y+1,z, w),
                new Point(x,y+1,z-1, w),

                new Point(x,y,z+1, w),
                new Point(x,y,z, w),
                new Point(x,y,z-1, w),

                new Point(x,y-1,z+1, w),
                new Point(x,y-1,z, w),
                new Point(x,y-1,z-1, w),
                //
                new Point(x-1,y+1,z+1, w),
                new Point(x-1,y+1,z, w),
                new Point(x-1,y+1,z-1, w),

                new Point(x-1,y,z+1, w),
                new Point(x-1,y,z, w),
                new Point(x-1,y,z-1, w),
                           
                new Point(x-1,y-1,z+1, w),
                new Point(x-1,y-1,z, w),
                new Point(x-1,y-1,z-1, w)
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;
        if (z != point.z) return false;
        return w == point.w;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + w;
        return result;
    }
}
/*
class Space {
    Map<Integer, Plane> planes = new HashMap<>();
    char get(int x, int y, int z){
        if(planes.containsKey(z))
            return planes.get(z).get(x, y);
        return '.';
    }
    void set(int x, int y, int z, char ch){
        planes.computeIfAbsent(z, k -> new Plane()).set(x, y, ch);
    }
}

class Plane {
    Map<Integer, Line> lines = new HashMap<>();
    char get(int x, int y) {
        if(lines.containsKey(y))
            return lines.get(y).get(x);
        return '.';
    }
    void set(int x, int y, char ch){
        lines.computeIfAbsent(y, k -> new Line()).set(x, ch);
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        lines.keySet().stream().sorted()
    }
}

class Line {
    Map<Integer, Character> points = new HashMap<>();
    char get(int x) {
        if(points.containsKey(x))
            return points.get(x);
        return '.';
    }
    void set(int x, char ch) {
        points.put(x, ch);
    }
    int getMinX(){

    }
}*/
