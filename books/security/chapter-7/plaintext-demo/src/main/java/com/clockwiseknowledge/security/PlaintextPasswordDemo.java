/*
 * Chapter 7 – Password Hashing and Salting
 *
 * This example demonstrates insecure plaintext password storage.
 *
 * The program accepts a password and stores it directly in a
 * simulated in-memory user record without hashing or encryption.
 *
 * This example is intentionally insecure and is provided only
 * for educational purposes. It demonstrates why passwords must
 * never be stored in plaintext in a real application.
 *
 * Author: Sanjay Ghosh
 */

package com.clockwiseknowledge.security;

public class PlaintextPasswordDemo {

    public static void main(String[] args) {

        // Simulated user credentials.
        String username = "alice";
        String password = "MySecret123";

        // Insecure: the password is stored directly as plaintext.
        String storedPassword = password;

        System.out.println("Username: " + username);
        System.out.println("Stored password: " + storedPassword);

        System.out.println();
        System.out.println("WARNING: The password is stored in plaintext.");
    }
}
