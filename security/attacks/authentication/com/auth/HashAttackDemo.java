package com.auth;

import java.security.MessageDigest;

public class HashAttackDemo {

    public static String hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(input.getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : digest) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public static void main(String[] args) throws Exception {

        String storedHash = hash("password");

        String[] guesses = {"123456", "password", "admin"};

        for (String guess : guesses) {
            if (hash(guess).equals(storedHash)) {
                System.out.println("==> Cracked: " + guess);
            }
        }
    }
}
