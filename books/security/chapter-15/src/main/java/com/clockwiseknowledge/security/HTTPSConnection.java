/*
 * Author: Sanjay Ghosh
 * Project: Practical Application Security
 * Chapter 15: TLS and SSL
 * Description: Sends an HTTPS GET request and displays
 *              the HTTP status and successful response body.
 * Java Version: 17
 */


package com.clockwiseknowledge.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HTTPSConnection {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnection conn =
                (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        try {
            int status = conn.getResponseCode();
            System.out.println("HTTP status: " + status);

            if (status >= 200 && status < 300) {
                try (BufferedReader reader =
                        new BufferedReader(new InputStreamReader(
                                conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        } finally {
            conn.disconnect();
        }
    }
}
