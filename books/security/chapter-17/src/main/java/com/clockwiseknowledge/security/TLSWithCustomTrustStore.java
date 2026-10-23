
package com.clockwiseknowledge.security;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.Console;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Arrays;

/**
 * Project: Software Engineering Knowledge Base
 * Chapter: 17 - Keystore and Truststore
 * Description: Connects to an HTTPS endpoint using a custom truststore.
 * Java Version: 17
 */
public class TLSWithCustomTrustStore {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println(
                "Usage: TLSWithCustomTrustStore "
                + "<truststore-path> <store-type> <https-url>");
            System.err.println(
                "Example: TLSWithCustomTrustStore "
                + "test-truststore.p12 PKCS12 https://localhost:8443");
            System.exit(1);
        }

        Path trustStorePath = Path.of(args[0]);
        String storeType = args[1];
        String endpoint = args[2];

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

            TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(
                null,
                trustManagerFactory.getTrustManagers(),
                null
            );

            URL url = new URL(endpoint);
            HttpsURLConnection connection =
                (HttpsURLConnection) url.openConnection();

            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            try {
                int status = connection.getResponseCode();

                System.out.println("URL: " + endpoint);
                System.out.println("HTTP status: " + status);
                System.out.println("TLS cipher suite: "
                    + connection.getCipherSuite());
                System.out.println("Server certificate subject: "
                    + connection.getServerCertificates()[0]);
            } finally {
                connection.disconnect();
            }

        } catch (Exception e) {
            System.err.println("HTTPS connection failed: "
                + e.getMessage());
            System.exit(1);
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}

