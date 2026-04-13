package com.auth;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SaltedHashDemo {

    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

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

        String password = "password";
        String salt = generateSalt();

        String hashed = hash(password, salt);

        System.out.println("Salt: " + salt);
        System.out.println("Hash: " + hashed);
    }
}
