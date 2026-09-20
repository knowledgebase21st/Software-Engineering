/*
 * Chapter 7 – Password Hashing and Salting
 *
 * This example demonstrates password-hash migration.
 *
 * The simulated application initially stores a user's password
 * using a legacy BCrypt configuration.
 *
 * During a successful login:
 *
 *     1. The supplied password is verified against the legacy hash.
 *     2. The supplied password is rehashed using the current
 *        Argon2id password-hashing algorithm.
 *     3. The stored password hash is replaced with the new hash.
 *     4. The new hash is verified to demonstrate the migration.
 *
 * The example also demonstrates an important migration principle:
 *
 *     The old password hash must NOT be hashed again with the
 *     new password-hashing algorithm.
 *
 * The original password supplied during successful authentication
 * must be used to create the new password hash.
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

public class PasswordMigrationDemo {

    public static void main(String[] args) {

        String password = "MySecret123";

        /*
         * Simulate the legacy password-storage configuration.
         *
         * In a real application, this hash would already exist
         * in the user database before the migration.
         */
        PasswordEncoder legacyEncoder =
                new BCryptPasswordEncoder(10);

        String storedPasswordHash =
                legacyEncoder.encode(password);

        System.out.println("PASSWORD MIGRATION DEMONSTRATION");
        System.out.println("================================");
        System.out.println();

        System.out.println("Legacy password-hashing algorithm: BCrypt");
        System.out.println("Stored legacy hash:");
        System.out.println(storedPasswordHash);
        System.out.println();

        /*
         * Simulate a user logging in with the correct password.
         */
        String suppliedPassword = "MySecret123";

        boolean passwordMatches =
                legacyEncoder.matches(
                        suppliedPassword,
                        storedPasswordHash
                );

        System.out.println(
                "Legacy password verification: "
                        + passwordMatches
        );

        /*
         * Only migrate the password after successful verification.
         */
        if (passwordMatches) {

            /*
             * Current password-storage configuration.
             *
             * The original password supplied by the user is
             * rehashed using the new password-hashing algorithm.
             */
            PasswordEncoder currentEncoder =
                    Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

            String migratedPasswordHash =
                    currentEncoder.encode(suppliedPassword);

            /*
             * Replace the old stored hash with the new hash.
             *
             * In a real application, this would be a database update.
             */
            storedPasswordHash = migratedPasswordHash;

            System.out.println();
            System.out.println(
                    "Password successfully migrated."
            );

            System.out.println(
                    "Current password-hashing algorithm: Argon2id"
            );

            System.out.println("New stored hash:");
            System.out.println(storedPasswordHash);

            /*
             * Verify the migrated password using the new algorithm.
             */
            boolean migratedPasswordMatches =
                    currentEncoder.matches(
                            suppliedPassword,
                            storedPasswordHash
                    );

            System.out.println();

            System.out.println(
                    "Migrated password verification: "
                            + migratedPasswordMatches
            );
        }
    }
}
