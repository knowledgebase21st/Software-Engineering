/*
 * =============================================================================
 * Chapter 6 – Authentication Attacks
 * Example: Brute Force Attack
 *
 * This example demonstrates how a brute-force attack systematically generates
 * possible passwords and attempts them against a single test account.
 *
 * Educational use only.
 * Author : Sanjay Ghosh
 * =============================================================================
 */

package com.practicalapplicationsecurity.bruteforce;

public class BruteForceDemo {

    private static final String USERNAME = "alice";
    private static final String CORRECT_PASSWORD = "cat";

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_PASSWORD_LENGTH = 3;

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("       Brute Force Authentication Demo");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Target account: " + USERNAME);
        System.out.println("Search space: lowercase letters");
        System.out.println("Maximum password length: " + MAX_PASSWORD_LENGTH);
        System.out.println();

        String password = findPassword();

        if (password != null) {
            System.out.println();
            System.out.println("Authentication successful!");
            System.out.println("Password found: " + password);
        } else {
            System.out.println();
            System.out.println("Password was not found.");
        }
    }

    private static String findPassword() {

        for (int length = 1; length <= MAX_PASSWORD_LENGTH; length++) {

            String password = generatePasswords("", length);

            if (password != null) {
                return password;
            }
        }

        return null;
    }

    private static String generatePasswords(String prefix, int remainingLength) {

        if (remainingLength == 0) {

            System.out.println("Trying password: " + prefix);

            if (authenticate(USERNAME, prefix)) {
                return prefix;
            }

            return null;
        }

        for (int i = 0; i < CHARACTERS.length(); i++) {

            char character = CHARACTERS.charAt(i);

            String password = generatePasswords(
                    prefix + character,
                    remainingLength - 1
            );

            if (password != null) {
                return password;
            }
        }

        return null;
    }

    private static boolean authenticate(String username, String password) {

        return USERNAME.equals(username)
                && CORRECT_PASSWORD.equals(password);
    }
}
