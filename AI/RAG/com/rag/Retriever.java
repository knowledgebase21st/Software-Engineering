/* Author: Sanjay Ghosh*
 * Copyright (c) 2026 Sanjay Ghosh
 * All rights reserved.
 * This retrieves from Vector DB by passing the string query
 * */

package com.rag;

import java.sql.*;
import java.util.*;

public class Retriever {

    private static final String URL = "jdbc:postgresql://localhost:5432/ragdb";
    private static final String USER = "postgres";
    private static final String PASS = "password";

    public static List<String> retrieve(String query, int topK) throws Exception {

        List<Double> queryEmbedding = EmbeddingService.getEmbedding(query);
        String vector = queryEmbedding.toString();

        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        PreparedStatement ps = conn.prepareStatement(
            """
            SELECT content
            FROM text_embeddings
            ORDER BY embedding <-> ?::vector
            LIMIT ?
            """
        );

        ps.setString(1, vector);
        ps.setInt(2, topK);

        ResultSet rs = ps.executeQuery();

        List<String> results = new ArrayList<>();

        while (rs.next()) {
            results.add(rs.getString("content"));
        }

        rs.close();
        ps.close();
        conn.close();

        return results;
    }
}
