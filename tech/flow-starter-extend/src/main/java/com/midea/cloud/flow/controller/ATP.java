package com.midea.cloud.flow.controller;

public class ATP {
    public static int sub(int[] A, int[] B) {
        int res = -1;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                int ele1 = A[i];
                int ele2 = B[j];
                if (ele2 != ele1) {
                    break;
                }
                if (j == B.length - 1) {
                    res = i;
                }
            }
        }
        return res;
    }

    public static String add(String numberA, String numberB) {
        if (!(isInt(numberA) & isInt(numberB))) {
            return "ERROR";
        }
        int numA = toNum(numberA);
        int numB = toNum(numberB);
        int temp = numA + numB;
        return String.valueOf(temp);
    }

    private static int toNum(String num) {
        int res = 0;
        int len = num.length() - 1;
        int factor = 10;
        for (int i = len; i > 0 ; i--) {
            String n = String.valueOf(num.charAt(i));
            int digit = Integer.parseInt(n);
            digit = digit * Integer.parseInt(String.valueOf((Math.pow(factor, (len - i)))));
            res += digit;
        }
        return res;
    }

    private static boolean isInt(String i) {
        String ii = "1234567890";
        for (int j = 0; j < ii.length(); j++) {
            String n = String.valueOf(ii.charAt(j));
            if (!ii.contains(n)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        System.out.println(add("123456789123456789123456789", "123456789123456789123456789"));
        // 246913578246913578246913578
    }
}
