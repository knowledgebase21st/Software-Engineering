
/*
 * Author: Sanjay Ghosh
 * Project: Practical Application Security
 * Chapter 15: TLS and SSL
 * Description: Demonstrates a TLS handshake and displays
 *              negotiated session and peer certificate details.
 * Java Version: 17
 */

package com.clockwiseknowledge.security;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TLSHandshakeInfo {

    public static void main(String[] args) throws Exception {
        String host = "example.com";
        int port = 443;

        SSLContext context = SSLContext.getDefault();
        SSLSocketFactory factory = context.getSocketFactory();

        try (SSLSocket socket =
                (SSLSocket) factory.createSocket(host, port)) {

            SSLParameters parameters = socket.getSSLParameters();
            parameters.setEndpointIdentificationAlgorithm("HTTPS");
            socket.setSSLParameters(parameters);

            System.out.println("Starting TLS handshake...");
            socket.startHandshake();

            SSLSession session = socket.getSession();

            System.out.println("\nTLS Handshake Successful");
            System.out.println("Server: " + host);
            System.out.println("TLS version: "
                    + session.getProtocol());
            System.out.println("Cipher suite: "
                    + session.getCipherSuite());
            System.out.println("Session creation time: "
                    + session.getCreationTime());

            Certificate[] certificates =
                    session.getPeerCertificates();

            System.out.println("\nPeer certificate chain:");
            System.out.println("Certificate count: "
                    + certificates.length);

            for (int i = 0; i < certificates.length; i++) {
                if (certificates[i] instanceof X509Certificate cert) {
                    System.out.println("\nCertificate " + (i + 1));
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
        }
    }
}
