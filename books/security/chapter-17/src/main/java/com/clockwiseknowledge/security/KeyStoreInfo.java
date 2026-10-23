
package com.clockwiseknowledge.security;

import java.io.Console;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Project: Software Engineering Knowledge Base
 * Chapter: 17 - Keystore and Truststore
 * Description: Displays keystore metadata and entry information
 *              without exposing private key material.
 * Java Version: 17
 */
public class KeyStoreInfo {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                "Usage: KeyStoreInfo <keystore-path> <store-type>");
            System.err.println("Example: KeyStoreInfo server-keystore.p12 PKCS12");
            System.exit(1);
        }

        Path storePath = Path.of(args[0]);
        String storeType = args[1];

        Console console = System.console();
        if (console == null) {
            System.err.println(
                "No console available. Run this program in a terminal.");
            System.exit(1);
        }

        char[] password = console.readPassword("Keystore password: ");

        try {
            KeyStore keyStore = KeyStore.getInstance(storeType);

            try (InputStream input = Files.newInputStream(storePath)) {
                keyStore.load(input, password);
            }

            System.out.println("Keystore: " + storePath.toAbsolutePath());
            System.out.println("Type: " + keyStore.getType());
            System.out.println("Entry count: " + keyStore.size());

            Enumeration<String> aliases = keyStore.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();

                System.out.println("\nAlias: " + alias);
                System.out.println("Entry type: "
                    + getEntryType(keyStore, alias));

                Certificate certificate =
                    keyStore.getCertificate(alias);

                if (certificate instanceof X509Certificate cert) {
                    System.out.println("Subject: "
                        + cert.getSubjectX500Principal());
                    System.out.println("Issuer: "
                        + cert.getIssuerX500Principal());
                    System.out.println("Valid from: "
                        + cert.getNotBefore());
                    System.out.println("Valid until: "
                        + cert.getNotAfter());
                }
            }

        } catch (Exception e) {
            System.err.println("Unable to inspect keystore: "
                + e.getMessage());
            System.exit(1);
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }

    private static String getEntryType(
            KeyStore keyStore, String alias) throws Exception {

        if (keyStore.isKeyEntry(alias)) {
            return "Key entry (private or secret key)";
        }

        if (keyStore.isCertificateEntry(alias)) {
            return "Trusted certificate entry";
        }

        return "Unknown entry type";
    }
}

