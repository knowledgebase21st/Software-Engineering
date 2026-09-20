/*
 * Chapter 7 – Password Hashing and Salting
 *
 * This example demonstrates the effect of the BCrypt work factor
 * on password verification time.
 *
 * The program:
 *
 *     1. Creates BCrypt password encoders with different costs.
 *     2. Generates a password hash for each cost.
 *     3. Verifies the password against each hash.
 *     4. Measures the verification time.
 *
 * The purpose is to demonstrate that increasing the work factor
 * increases the computational cost of password verification.
 *
 * The measured timings depend on the hardware, operating system,
 * Java runtime, system load, and other environmental conditions.
 *
 * These measurements are illustrative and are not universal
 * performance benchmarks or security rankings.
 *
 * Author: Sanjay Ghosh
 */

package com.clockwiseknowledge.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class WorkFactorDemo {

    public static void main(String[] args) {

        String password = "MySecret123";

        int[] workFactors = {10, 11, 12};

        System.out.println("BCrypt Work-Factor Demonstration");
        System.out.println("================================");
        System.out.println();

        for (int workFactor : workFactors) {

            BCryptPasswordEncoder encoder =
                    new BCryptPasswordEncoder(workFactor);

            /*
             * Generate the password hash.
             *
             * This operation also consumes computational resources,
             * but the demonstration focuses on verification time.
             */
            String encodedPassword =
                    encoder.encode(password);

            /*
             * Measure password verification time.
             */
            long startTime = System.nanoTime();

            boolean matches =
                    encoder.matches(password, encodedPassword);

            long endTime = System.nanoTime();

            double verificationTime =
                    (endTime - startTime) / 1_000_000.0;

            System.out.println("Work factor: " + workFactor);
            System.out.println("Password verified: " + matches);
            System.out.printf(
                    "Verification time: %.2f ms%n",
                    verificationTime
            );
            System.out.println();
        }

        System.out.println(
                "Note: Timings are specific to the current "
                        + "execution environment."
        );
    }
}
