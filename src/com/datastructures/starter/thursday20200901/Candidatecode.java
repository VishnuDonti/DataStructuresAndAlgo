package com.datastructures.starter.thursday20200901;

import java.math.BigInteger;
import java.util.*;

public class Candidatecode {

    private static BigInteger mod = new BigInteger("1000000007" , 10);


    public static void main(String args[] ) throws Exception {
        Scanner sc = new Scanner(System.in);
        BigInteger rows = sc.nextBigInteger();
        BigInteger columns = sc.nextBigInteger();
        int t = sc.nextInt();
        sc.close();
        System.out.print(calculateWaysToFill(rows, columns, t));
    }

    private static BigInteger calculateWaysToFill(BigInteger n , BigInteger m , int t) {
        if( n.add(m).mod(BigInteger.TWO).intValue() == 1 && t == -1) {
            return BigInteger.ZERO;
        }
        if(n.intValue() == 1 && m.intValue() == 1) {
            return BigInteger.ONE;
        }
        BigInteger product = n.subtract(BigInteger.ONE).multiply(m.subtract(BigInteger.ONE));
        return BigInteger.TWO.modPow(product,mod);
    }
}