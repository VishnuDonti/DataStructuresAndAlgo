package com.datastructures.starter.Sunday20191226;

import java.util.Arrays;

public class PossiblePalindrome {

    public static void main(String[] args) {
        String input = "123455346785";
        int maxLengthSoFar = 0;
        for (int i = 1; i < input.length(); i++) {
           int maxLength = getMaxPalindome(input,i-1,i);
           if( maxLength > maxLengthSoFar) {
               maxLengthSoFar = maxLength;
           }

        }
        System.out.println(maxLengthSoFar);
    }

    public static int getMaxPalindome(String str, int left , int right) {
        int maxLength = 0;
        while( left >= 0 && right < str.length() ) {
            if(isEligible(str,left,right)) {
                maxLength =  right-left + 1;
            }
            left--;
            right++;
        }
        return maxLength;
    }

    private static boolean isEligible(String str, int left, int right) {
        byte[]  bb = str.substring(left,right+1).getBytes();
        Arrays.parallelSort(bb);
        for(int i = 0 ; i <= bb.length-2 ; i+=2 ) {
            if(bb[i] != bb[i+1]) {
                return false;
            }
        }
        return true;
    }

}
