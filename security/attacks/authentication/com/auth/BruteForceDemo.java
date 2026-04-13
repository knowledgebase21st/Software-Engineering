package com.auth;

public class BruteForceDemo {

    public static void main(String[] args) {

        String actualPassword = "1234";

        for (int i = 0; i <= 9999; i++) {
            String guess = String.format("%04d", i);

            System.out.println("Trying: " + guess);

            if (guess.equals(actualPassword)) {
                System.out.println("==> Password found: " + guess);
                break;
            }
        }
    }
}
