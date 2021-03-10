package com.datastructures.starter.saturday20191214;

import java.util.HashMap;
import java.util.Map;

public class ways {

    public static Map<Integer,Integer> waysMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println(ways(20));
    }

    public static int ways(int n) {
        System.out.println("Calling at the start  for " + n);
        if (n<0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } if (!waysMap.containsKey(n)){
             System.out.println("Calling for " + n);
             waysMap.put(n, ways(n-1) + ways(n-2));
        }
        return waysMap.get(n);
    }
}
