package com.datastructures.starter.tuesday20191210;

import java.util.HashMap;

public class NumberofWays {

    // hashMap to hold the factorial values
    public static HashMap<Integer, Integer> factorialMap = new HashMap<>();

    public static void main(String[] args) {
        // factorial value for 0
        factorialMap.put(0,1);
        // factorial value for 1
        factorialMap.put(1,1);
        // input number of balls
        int n = 9;
        // final number of ways to achieve pull
        int numberOfWays = 1;
        // ones and twos for now
        // TODO: Need to check if it can be generalized.
        int twos = 0;
        // int maxballsAtOnce = 3;
        while (n>1){
            n= n - 2;
            twos++;
            numberOfWays +=  factorial(n + twos )  / ( factorial( twos ) * factorial( n ) );
        }
        System.out.println(numberOfWays);
    }

    private static int factorial(int i) {
        /* Memoization */
        if (factorialMap.get(i) == null) {
            factorialMap.putIfAbsent(i, i * factorial(i - 1));
        }
        return factorialMap.get(i);
    }

}
