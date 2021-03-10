package com.datastructures.starter.thursday20200901;

import java.util.*;

public class CandidateCode1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] NC = sc.nextLine().split(" ");
        int N = Integer.valueOf(NC[0]);
        int C = Integer.valueOf(NC[1]);
        List<String>  crcReq = new ArrayList<>();
        for( int m = 0 ; m <= (C-1) ; m++) {
            crcReq.add(sc.nextLine());
        }
        Collections.sort(crcReq);
        int sold = 0;
        for( int m = 0 ; m <= (N-1) ; m++) {
            int w1 = sc.nextInt();
            int p1 = sc.nextInt();
            String selectedWeight = "";
            for(String we : crcReq) {
               String[] WP = we.split(" ");
                if( w1 >= Integer.valueOf(WP[0]) && p1 <= Integer.valueOf(WP[1])) {
                    selectedWeight = we;
                    break;
                }
            }
            if(!selectedWeight.equals("")) {
                sold++;
                crcReq.remove(selectedWeight);
            }
        }
        System.out.println(sold);
    }
}
