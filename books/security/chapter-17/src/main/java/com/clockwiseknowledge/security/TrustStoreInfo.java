
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
 * Description: Displays trusted certificate entries in a truststore.
 * Java Version: 17
 */
public class TrustStoreInfo {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                "Usage: TrustStoreInfo <truststore-path> <store-type>");
            System.err.println(
                "Example: TrustStoreInfo client-truststore.p12 PKCS12");
            System.exit(1);
        }

        Path trustStorePath = Path.of(args[0]);
        String storeType = args[1];

        Console console = System.console();
        if (console == null) {
            System.err.println(
                "No console available. Run this program in a terminal.");
            System.exit(1);
        }

        char[] password = console.readPassword("Truststore password: ");

        try {
            KeyStore trustStore = KeyStore.getInstance(storeType);

            try (InputStream input = Files.newInputStream(trustStorePath)) {
                trustStore.load(input, password);
            }

            System.out.println(
                "Truststore: " + trustStorePath.toAbsolutePath());
            System.out.println("Type: " + trustStore.getType());
            System.out.println("Entry count: " + trustStore.size());

            Enumeration<String> aliases = trustStore.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();

                System.out.println("\nAlias: " + alias);

                if (trustStore.isCertificateEntry(alias)) {
                    System.out.println("Entry type: Trusted certificate");
                } else if (trustStore.isKeyEntry(alias)) {
                    System.out.println(
                        "Entry type: Key entry (not a trusted-certificate entry)");
                } else {
                    System.out.println("Entry type: Other");
                }

                Certificate certificate =
                    trustStore.getCertificate(alias);

                if (certificate instanceof X509Certificate cert) {
                    System.out.println("Subject: "
                        + cert.getSubjectX500Principal());
                    System.out.println("Issuer: "
                        + cert.getIssuerX500Principal());
                    System.out.println("Valid from: "
                        + cert.getNotBefore());
                    System.out.println("Valid until: "
                        + cert.getNotAfter());
                    System.out.println("Currently within validity dates: "
                        + isCurrentlyValid(cert));
                }
            }

        } catch (Exception e) {
            System.err.println("Unable to inspect truststore: "
                + e.getMessage());
            System.exit(1);
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }

    private static boolean isCurrentlyValid(X509Certificate certificate) {
        try {
            certificate.checkValidity();
            return true;
        } catch (java.security.cert.CertificateExpiredException
                 | java.security.cert.CertificateNotYetValidException e) {
            return false;
        }
    }
}

