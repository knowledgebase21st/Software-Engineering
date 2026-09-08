/*
 * =============================================================================
 * Chapter 6 – Authentication Attacks
 * Example: Dictionary Attack
 *
 * This example demonstrates how a dictionary attack uses a predefined list
 * of likely passwords and attempts them against a single test account.
 *
 * Educational use only.
 * Author : Sanjay Ghosh
 * =============================================================================
 */

package com.practicalapplicationsecurity.dictionaryattack;

public class DictionaryAttackDemo {

    private static final String USERNAME = "alice";
    private static final String CORRECT_PASSWORD = "welcome123";

    public static void main(String[] args) {

        String[] dictionary = {
                "password",
                "123456",
                "admin",
                "qwerty",
                "letmein",
                "welcome",
                "welcome123",
                "monkey",
                "login",
                "password123"
        };

        System.out.println("==========================================");
        System.out.println("      Dictionary Attack Demo");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Target account: " + USERNAME);
        System.out.println("Dictionary entries: " + dictionary.length);
        System.out.println();

        boolean authenticated = false;

        for (String password : dictionary) {

            System.out.println("Trying dictionary password: " + password);

            if (authenticate(USERNAME, password)) {
                System.out.println();
                System.out.println("Authentication successful!");
                System.out.println("Password found in dictionary: " + password);

                authenticated = true;
                break;
            }
        }

        if (!authenticated) {
            System.out.println();
            System.out.println("Authentication failed.");
            System.out.println("Password was not found in the dictionary.");
        }
    }

    private static boolean authenticate(String username, String password) {

        return USERNAME.equals(username)
                && CORRECT_PASSWORD.equals(password);
    }
}
