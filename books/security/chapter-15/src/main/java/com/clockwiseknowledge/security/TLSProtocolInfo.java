/*
 * Author: Sanjay Ghosh
 * Project: Practical Application Security
 * Chapter 15: TLS and SSL
 * Description: Displays TLS protocols and cipher suites
 *              supported and enabled by the Java runtime.
 * Java Version: 17
 */

package com.clockwiseknowledge.security;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

public class TLSProtocolInfo {

    public static void main(String[] args) throws Exception {
        SSLContext context = SSLContext.getDefault();

        SSLParameters supported =
                context.getSupportedSSLParameters();
        SSLParameters defaults =
                context.getDefaultSSLParameters();

        System.out.println("Supported protocols:");
        for (String protocol : supported.getProtocols()) {
            System.out.println("  " + protocol);
        }

        System.out.println("\nDefault enabled protocols:");
        for (String protocol : defaults.getProtocols()) {
            System.out.println("  " + protocol);
        }

        System.out.println("\nDefault cipher suites:");
        for (String suite : defaults.getCipherSuites()) {
            System.out.println("  " + suite);
        }
    }
}
