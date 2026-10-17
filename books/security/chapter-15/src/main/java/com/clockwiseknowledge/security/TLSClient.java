/*
 * Author: Sanjay Ghosh
 * Project: Practical Application Security
 * Chapter 15: TLS and SSL
 * Description: Establishes a TLS socket connection and
 *              displays the negotiated protocol and cipher suite.
 * Java Version: 17
 */

package com.clockwiseknowledge.security;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TLSClient {

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

            socket.startHandshake();

            System.out.println("TLS version: "
                    + socket.getSession().getProtocol());

            System.out.println("Cipher suite: "
                    + socket.getSession().getCipherSuite());
        }
    }
}
