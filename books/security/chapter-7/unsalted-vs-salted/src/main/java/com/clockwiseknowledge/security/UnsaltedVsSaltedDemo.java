/*
 * Chapter 7 – Password Hashing and Salting
 *
 * This example demonstrates the difference between unsalted
 * and salted password hashes.
 *
 * The program hashes the same password without a salt and then
 * hashes the same password using two different randomly generated
 * salts. It demonstrates that:
 *
 *   1. The same password produces the same unsalted hash.
 *   2. The same password produces different salted hashes when
 *      different salts are used.
 *
 * SHA-256 is used here only to demonstrate the effect of salting.
 * SHA-256 is a fast general-purpose hash and must not be used
 * as the password-storage mechanism in a production application.
 *
 * Author: Sanjay Ghosh
 */

package com.clockwiseknowledge.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;

public class UnsaltedVsSaltedDemo {

    public static void main(String[] args) throws Exception {

        String password = "MySecret123";

        System.out.println("Password: " + password);
        System.out.println();

        // ---------------------------------------------------------
        // Unsalted hashing
        // ---------------------------------------------------------

        String unsaltedHash1 = sha256(password);
        String unsaltedHash2 = sha256(password);

        System.out.println("UNSALTED HASHING");
        System.out.println("----------------");
        System.out.println("Hash 1: " + unsaltedHash1);
        System.out.println("Hash 2: " + unsaltedHash2);
        System.out.println("Hashes equal: " + unsaltedHash1.equals(unsaltedHash2));

        System.out.println();

        // ---------------------------------------------------------
        // Salted hashing
        // ---------------------------------------------------------

        SecureRandom secureRandom = new SecureRandom();

        byte[] salt1 = new byte[16];
        byte[] salt2 = new byte[16];

        secureRandom.nextBytes(salt1);
        secureRandom.nextBytes(salt2);

        String saltedHash1 = sha256(password, salt1);
        String saltedHash2 = sha256(password, salt2);

        System.out.println("SALTED HASHING");
        System.out.println("--------------");
        System.out.println("Salt 1: " + HexFormat.of().formatHex(salt1));
        System.out.println("Hash 1: " + saltedHash1);
        System.out.println();

        System.out.println("Salt 2: " + HexFormat.of().formatHex(salt2));
        System.out.println("Hash 2: " + saltedHash2);
        System.out.println();

        System.out.println("Hashes equal: " + saltedHash1.equals(saltedHash2));
    }

    /*
     * Computes a SHA-256 hash of the supplied password.
     *
     * This method is included only to demonstrate the effect
     * of salting. It is not suitable for production password
     * storage.
     */
    private static String sha256(String password) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8)
        );

        return HexFormat.of().formatHex(hash);
    }

    /*
     * Computes a SHA-256 hash of the salt followed by the password.
     *
     * This demonstrates how a salt changes the resulting hash.
     * In a production application, use a password-specific hashing
     * algorithm such as Argon2id, scrypt, bcrypt, or PBKDF2 instead
     * of implementing this construction manually.
     */
    private static String sha256(String password, byte[] salt)
            throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        digest.update(salt);

        byte[] hash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8)
        );

        return HexFormat.of().formatHex(hash);
    }
}
