/*
 * =============================================================================
 * Chapter 6 – Authentication Attacks
 * Example: Rainbow Table Attack
 *
 * This example demonstrates the concept of using precomputed password hashes
 * to look up a password when a password hash has been obtained.
 *
 * Educational use only.
 * Author : Sanjay Ghosh
 * =============================================================================
 */

package com.practicalapplicationsecurity.rainbowtable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RainbowTableDemo {

    public static void main(String[] args) {

        String username = "alice";
        String actualPassword = "welcome123";

        /*
         * This represents a small precomputed rainbow table.
         *
         * In a real attack, such a table could contain a very large
         * number of password/hash pairs.
         */
        Map<String, String> rainbowTable = createRainbowTable();

        /*
         * Assume the attacker has obtained the password hash
         * from an insecurely stored password database.
         */
        String stolenHash = hash(actualPassword);

        System.out.println("==========================================");
        System.out.println("        Rainbow Table Demo");
        System.out.println("==========================================");
        System.out.println();

        System.out.println("Target account: " + username);
        System.out.println("Stolen password hash:");
        System.out.println(stolenHash);
        System.out.println();

        System.out.println("Searching precomputed table...");

        String password = rainbowTable.get(stolenHash);

        if (password != null) {
            System.out.println();
            System.out.println("Matching password found!");
            System.out.println("Password: " + password);
        } else {
            System.out.println();
            System.out.println("No matching password found.");
        }
    }

    private static Map<String, String> createRainbowTable() {

        Map<String, String> table = new HashMap<>();

        String[] passwords = {
                "123456",
                "password",
                "admin",
                "welcome",
                "qwerty",
                "welcome123",
                "letmein"
        };

        for (String password : passwords) {
            String hash = hash(password);
            table.put(hash, password);
        }

        return table;
    }

    private static String hash(String password) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes =
                    digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
