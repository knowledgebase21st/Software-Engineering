/*
 * Chapter 7 – Password Hashing and Salting
 *
 * This example demonstrates secure password verification.
 *
 * The program:
 *
 *   1. Creates a password hash using BCryptPasswordEncoder.
 *   2. Simulates storing the generated hash.
 *   3. Verifies the correct password.
 *   4. Verifies an incorrect password.
 *
 * The original password is never recovered from the stored hash.
 * Verification is performed using the password-hashing library.
 *
 * Author: Sanjay Ghosh
 */

package com.clockwiseknowledge.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordVerificationDemo {

    public static void main(String[] args) {

        BCryptPasswordEncoder passwordEncoder =
                new BCryptPasswordEncoder();

        String password = "MySecret123";

        // Create a password hash for storage.
        String storedPasswordHash =
                passwordEncoder.encode(password);

        System.out.println("Password hash stored by the application:");
        System.out.println(storedPasswordHash);

        System.out.println();

        // Simulate a user entering the correct password.
        String correctPassword = "MySecret123";

        boolean correctPasswordMatches =
                passwordEncoder.matches(
                        correctPassword,
                        storedPasswordHash
                );

        System.out.println(
                "Correct password verification: "
                        + correctPasswordMatches
        );

        // Simulate a user entering an incorrect password.
        String incorrectPassword = "WrongPassword";

        boolean incorrectPasswordMatches =
                passwordEncoder.matches(
                        incorrectPassword,
                        storedPasswordHash
                );

        System.out.println(
                "Incorrect password verification: "
                        + incorrectPasswordMatches
        );
    }
}
