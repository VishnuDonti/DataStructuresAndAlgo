package com.datastructures.starter.sat20200620;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

public class Mean {

    public static void main(String[] args) {
        List<Integer> integers = asList(4, 889, 767, 987);
        double sum = integers.stream().mapToInt(x -> x).sum() /(integers.size() * 1d);
        double sum1 = integers.stream().mapToDouble(x -> (x - sum) * (x - sum)).sum();
        System.out.println(Math.sqrt(sum1/(integers.size() * 1d)));
    }
}
