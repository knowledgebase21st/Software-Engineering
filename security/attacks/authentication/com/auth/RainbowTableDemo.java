package com.auth;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class RainbowTableDemo {

    public static String hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : digest) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public static void main(String[] args) throws Exception {

        String[] passwords = {"123456", "password", "admin"};

        Map<String, String> table = new HashMap<>();

        for (String pwd : passwords) {
            table.put(hash(pwd), pwd);
        }

        String stolenHash = hash("password");

        if (table.containsKey(stolenHash)) {
            System.out.println("==> Instantly cracked: " + table.get(stolenHash));
        }
    }
}
