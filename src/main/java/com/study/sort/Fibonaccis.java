package com.study.sort;

import java.util.Arrays;

public class Fibonaccis {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(geneateFibonaccis(20)));
    }

    public static long[] geneateFibonaccis(int n) {
        if (n == 1) {
            return new long[]{1};
        }
        if ( n == 2) {
            return new long[]{1, 1};
        }
        long[] f = new long[n];
        f[0] = 1;
        f[1] = 1;
        for (int i=2; i<n; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f;
    }
}
