
package com.clockwiseknowledge.security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
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
 * Description: Runs a local TLS server using a PKCS12 keystore.
 * Java Version: 17
 *
 * Educational use only. Not intended for production deployment.
 */
public class MockTLSServer {

    private static final int DEFAULT_PORT = 8443;

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.err.println(
                "Usage: MockTLSServer <keystore-path> <store-type> [port]");
            System.err.println(
                "Example: MockTLSServer generated/server-keystore.p12 "
                    + "PKCS12 8443");
            System.exit(1);
        }

        Path keyStorePath = Path.of(args[0]);
        String storeType = args[1];
        int port;

        try {
            port = args.length == 3
                ? Integer.parseInt(args[2])
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

        char[] password = console.readPassword("Keystore password: ");

        try {
            runServer(keyStorePath, storeType, password, port);
        } catch (Exception e) {
            System.err.println("TLS server failed: " + e.getMessage());
            System.exit(1);
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private static void runServer(
            Path keyStorePath,
            String storeType,
            char[] password,
            int port) throws Exception {

        KeyStore keyStore = KeyStore.getInstance(storeType);

        try (InputStream input = Files.newInputStream(keyStorePath)) {
            keyStore.load(input, password);
        }

        KeyManagerFactory keyManagerFactory =
            KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
            keyManagerFactory.getKeyManagers(),
            null,
            null
        );

        try (SSLServerSocket serverSocket =
                 (SSLServerSocket) sslContext.getServerSocketFactory()
                     .createServerSocket(port)) {

            System.out.println(
                "Mock TLS server listening on port " + port);
            System.out.println(
                "Waiting for one client connection...");

            // This demonstration server handles one connection, then exits.
            try (SSLSocket clientSocket =
                     (SSLSocket) serverSocket.accept()) {

                clientSocket.setSoTimeout(10000);
                clientSocket.startHandshake();

                System.out.println("TLS handshake successful.");
                System.out.println(
                    "Protocol: "
                        + clientSocket.getSession().getProtocol());
                System.out.println(
                    "Cipher suite: "
                        + clientSocket.getSession().getCipherSuite());

                handleHttpRequest(clientSocket);
            }
        }
    }

    private static void handleHttpRequest(SSLSocket socket)
            throws Exception {

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                socket.getInputStream(),
                StandardCharsets.US_ASCII));

        String requestLine = reader.readLine();

        if (requestLine == null) {
            return;
        }

        System.out.println("Request: " + requestLine);

        // Read the remaining HTTP headers until the blank line.
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            // Headers are intentionally not logged.
        }

        byte[] body = (
            "Hello from the Chapter 17 mock TLS server!\n"
        ).getBytes(StandardCharsets.UTF_8);

        String headers =
            "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/plain; charset=UTF-8\r\n"
            + "Content-Length: " + body.length + "\r\n"
            + "Connection: close\r\n"
            + "\r\n";

        OutputStream output = socket.getOutputStream();
        output.write(headers.getBytes(StandardCharsets.US_ASCII));
        output.write(body);
        output.flush();

        System.out.println("HTTP response sent.");
    }
}

