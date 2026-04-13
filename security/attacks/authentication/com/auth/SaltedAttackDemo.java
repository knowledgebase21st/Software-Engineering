package com.auth;

import java.security.MessageDigest;

public class SaltedAttackDemo {

    public static String hash(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest((password + salt).getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : digest) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public static void main(String[] args) throws Exception {

        String salt = "randomSalt123";
        String storedHash = hash("password", salt);

        String[] dictionary = {"123456", "password", "admin"};

        for (String guess : dictionary) {
            if (hash(guess, salt).equals(storedHash)) {
                System.out.println("==> Cracked even with salt: " + guess);
            }
        }
    }
}
