/* Author: Sanjay Ghosh*
 * Copyright (c) 2026 Sanjay Ghosh
 * All rights reserved.
 * This stores to DB in Vector format
 * */

package com.rag;

import java.sql.*;
import java.util.List;

public class StorageService {

    private static final String URL = "jdbc:postgresql://localhost:5432/ragdb";
    private static final String USER = "postgres";
    private static final String PASS = "password";

    public static void storeText(String text) throws Exception {

        List<Double> embedding = EmbeddingService.getEmbedding(text);

        // Convert to PostgreSQL vector format: [1.0,2.0,...]
        String vector = embedding.toString();

        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO text_embeddings (content, embedding) VALUES (?, ?::vector)"
        );

        ps.setString(1, text);
        ps.setString(2, vector);

        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}
