
package com.clockwiseknowledge.security;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Arrays;

/**
 * Project: Software Engineering Knowledge Base
 * Chapter: 17 - Keystore and Truststore
 * Description: Connects to a mock TLS server, validates its identity,
 *              and exchanges an application-level message.
 * Java Version: 17
 *
 * Educational use only. The client preserves certificate and hostname
 * verification.
 */
public class MockTLSClient {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8443;

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 4) {
            System.err.println(
                "Usage: MockTLSClient <truststore-path> <store-type> "
                + "[host] [port]");
            System.err.println(
                "Example: MockTLSClient "
                + "generated/client-truststore.p12 PKCS12 localhost 8443");
            System.exit(1);
        }

        Path trustStorePath = Path.of(args[0]);
        String storeType = args[1];
        String host = args.length >= 3 ? args[2] : DEFAULT_HOST;
        int port;

        try {
            port = args.length == 4
                ? Integer.parseInt(args[3])
                : DEFAULT_PORT;

            if (port < 1 || port > 65535) {
                throw new IllegalArgumentException(
                    "Port must be between 1 and 65535.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid port: " + e.getMessage());
            System.exit(1);
            return;
        }

        Console console = System.console();
        if (console == null) {
            System.err.println(
                "No console available. Run this program in a terminal.");
            System.exit(1);
            return;
        }

        char[] password = console.readPassword("Truststore password: ");

        try {
            runClient(trustStorePath, storeType, password, host, port);
        } catch (Exception e) {
            System.err.println("TLS client failed: " + e.getMessage());
            System.exit(1);
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private static void runClient(
            Path trustStorePath,
            String storeType,
            char[] password,
            String host,
            int port) throws Exception {

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

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();

        try (SSLSocket socket =
                 (SSLSocket) socketFactory.createSocket(host, port)) {

            socket.setSoTimeout(10000);

            // Enforce HTTPS hostname verification during the TLS handshake.
            var sslParameters = socket.getSSLParameters();
            sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
            socket.setSSLParameters(sslParameters);

            System.out.println("Connecting to " + host + ":" + port);
            socket.startHandshake();

            System.out.println("TLS handshake successful.");
            System.out.println(
                "Protocol: " + socket.getSession().getProtocol());
            System.out.println(
                "Cipher suite: " + socket.getSession().getCipherSuite());

            // Send an HTTP request as application data over TLS.
            OutputStream output = socket.getOutputStream();
            String request =
                "GET / HTTP/1.1\r\n"
                + "Host: " + host + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";

            output.write(request.getBytes(StandardCharsets.US_ASCII));
            output.flush();

            System.out.println("\nServer response:");

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream(),
                    StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}

