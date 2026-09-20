/*
 * Chapter 7 – Password Hashing and Salting
 *
 * This example demonstrates four password-specific hashing
 * algorithms using established Spring Security implementations:
 *
 *     1. BCrypt
 *     2. SCrypt
 *     3. PBKDF2
 *     4. Argon2
 *
 * The program:
 *
 *     - Creates a password hash using each algorithm.
 *     - Displays the encoded password value.
 *     - Measures password verification time.
 *     - Verifies the correct password.
 *     - Verifies an incorrect password.
 *
 * The verification timings are illustrative only. They must not
 * be interpreted as a security ranking or comparison of the
 * algorithms.
 *
 * Password-hashing parameters should be benchmarked and tuned
 * on hardware representative of the target production environment.
 *
 * The examples use established library implementations rather
 * than implementing password-hashing algorithms manually.
 *
 * The password used in this demonstration is synthetic and must
 * not be replaced with a real production password.
 *
 * Author: Sanjay Ghosh
 */

package com.clockwiseknowledge.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class PasswordAlgorithmsDemo {

    public static void main(String[] args) {

        String password = "MySecret123";
        String incorrectPassword = "WrongPassword";

        testAlgorithm(
                "BCrypt",
                new BCryptPasswordEncoder(),
                password,
                incorrectPassword
        );

        testAlgorithm(
                "SCrypt",
                SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8(),
                password,
                incorrectPassword
        );

        testAlgorithm(
                "PBKDF2",
                Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8(),
                password,
                incorrectPassword
        );

        testAlgorithm(
                "Argon2id",
                Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8(),
                password,
                incorrectPassword
        );
    }

    private static void testAlgorithm(
            String algorithm,
            PasswordEncoder encoder,
            String password,
            String incorrectPassword) {

        System.out.println("========================================");
        System.out.println(algorithm);
        System.out.println("========================================");

        /*
         * Generate a password hash.
         *
         * This represents what an application might do when
         * creating a new password or changing an existing password.
         */
        String encodedPassword = encoder.encode(password);

        System.out.println("Encoded password:");
        System.out.println(encodedPassword);
        System.out.println();

        /*
         * Verify the correct password.
         *
         * The password is verified against the stored hash.
         * The original password is not recovered.
         */
        long startTime = System.nanoTime();

        boolean correctPasswordMatches =
                encoder.matches(password, encodedPassword);

        long endTime = System.nanoTime();

        double correctVerificationTime =
                (endTime - startTime) / 1_000_000.0;

        /*
         * Verify an incorrect password.
         */
        startTime = System.nanoTime();

        boolean incorrectPasswordMatches =
                encoder.matches(
                        incorrectPassword,
                        encodedPassword
                );

        endTime = System.nanoTime();

        double incorrectVerificationTime =
                (endTime - startTime) / 1_000_000.0;

        System.out.println(
                "Correct password verification: "
                        + correctPasswordMatches
        );

        System.out.printf(
                "Correct verification time: %.2f ms%n",
                correctVerificationTime
        );

        System.out.println(
                "Incorrect password verification: "
                        + incorrectPasswordMatches
        );

        System.out.printf(
                "Incorrect verification time: %.2f ms%n",
                incorrectVerificationTime
        );

        System.out.println();
    }
}
