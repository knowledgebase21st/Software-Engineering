/*
 * =============================================================================
 * Chapter 6 – Authentication Attacks
 * Example: Password Spraying Attack
 *
 * This example demonstrates how a password spraying attack uses a small set
 * of common passwords against multiple test accounts.
 *
 * Educational use only.
 * Author : Sanjay Ghosh
 * =============================================================================
 */

package com.practicalapplicationsecurity.passwordspraying;

import java.util.LinkedHashMap;
import java.util.Map;

public class PasswordSprayingDemo {

    private static final String COMMON_PASSWORD = "Welcome123";

    public static void main(String[] args) {

        Map<String, String> accounts = new LinkedHashMap<>();

        accounts.put("alice", "Spring2026");
        accounts.put("bob", "Welcome123");
        accounts.put("charlie", "Database99");
        accounts.put("david", "SecurePass7");
        accounts.put("emma", "Password88");

        System.out.println("==========================================");
        System.out.println("        Password Spraying Demo");
        System.out.println("==========================================");
        System.out.println();

        System.out.println("Common password being tested: "
                + COMMON_PASSWORD);
        System.out.println("Number of accounts: "
                + accounts.size());
        System.out.println();

        for (String username : accounts.keySet()) {

            System.out.println("Trying password against account: "
                    + username);

            if (authenticate(username, COMMON_PASSWORD, accounts)) {

                System.out.println();
                System.out.println("Authentication successful!");
                System.out.println("Compromised account: " + username);
                System.out.println("Password: " + COMMON_PASSWORD);
            }
        }

        System.out.println();
        System.out.println("Password spraying demonstration completed.");
    }

    private static boolean authenticate(
            String username,
            String password,
            Map<String, String> accounts) {

        String storedPassword = accounts.get(username);

        return storedPassword != null
                && storedPassword.equals(password);
    }
}
