/*
 * =============================================================================
 * Chapter 6 – Authentication Attacks
 * Example: Credential Stuffing Attack
 *
 * This example demonstrates how previously compromised username/password
 * pairs can be reused against a different target application.
 *
 * Educational use only.
 * Author : Sanjay Ghosh
 * =============================================================================
 */

package com.practicalapplicationsecurity.credentialstuffing;

import java.util.LinkedHashMap;
import java.util.Map;

public class CredentialStuffingDemo {

    public static void main(String[] args) {

        /*
         * These represent credentials obtained from a previous
         * data breach at another service.
         *
         * In a real credential-stuffing attack, the attacker
         * would use a much larger collection of compromised
         * credential pairs.
         */
        Map<String, String> stolenCredentials = new LinkedHashMap<>();

        stolenCredentials.put("alice", "Summer2025");
        stolenCredentials.put("bob", "Welcome123");
        stolenCredentials.put("charlie", "Database99");
        stolenCredentials.put("david", "SecurePass7");
        stolenCredentials.put("emma", "Password88");

        /*
         * These represent accounts in the target application.
         *
         * Some users have reused their passwords from another
         * service, creating the possibility of account compromise.
         */
        Map<String, String> targetAccounts = new LinkedHashMap<>();

        targetAccounts.put("alice", "DifferentPassword1");
        targetAccounts.put("bob", "Welcome123");
        targetAccounts.put("charlie", "AnotherPassword2");
        targetAccounts.put("david", "DifferentPassword3");
        targetAccounts.put("emma", "AnotherPassword4");

        System.out.println("==========================================");
        System.out.println("       Credential Stuffing Demo");
        System.out.println("==========================================");
        System.out.println();

        System.out.println("Testing previously compromised credentials");
        System.out.println("against a different target application.");
        System.out.println();

        for (Map.Entry<String, String> credential
                : stolenCredentials.entrySet()) {

            String username = credential.getKey();
            String password = credential.getValue();

            System.out.println("Trying credentials for: " + username);

            if (authenticate(username, password, targetAccounts)) {

                System.out.println("  Authentication successful!");
                System.out.println("  Compromised account: " + username);
                System.out.println("  Reused password was accepted.");
            } else {
                System.out.println("  Authentication failed.");
            }

            System.out.println();
        }

        System.out.println("Credential stuffing demonstration completed.");
    }

    private static boolean authenticate(
            String username,
            String password,
            Map<String, String> targetAccounts) {

        String storedPassword = targetAccounts.get(username);

        return storedPassword != null
                && storedPassword.equals(password);
    }
}
