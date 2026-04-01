/* Author: Sanjay Ghosh
 * Copyright (c) 2026 Sanjay Ghosh
 * All rights reserved.
 * This convrets the text into Vector 
 * the vector is used later.
 * */

package com.rag;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import org.json.*;

import java.io.InputStream;
import java.util.*;

public class EmbeddingService {

    public static List<Double> getEmbedding(String text) throws Exception {

        JSONObject body = new JSONObject();
        body.put("model", "nomic-embed-text");
        body.put("prompt", text);

        ClassicHttpResponse response = (ClassicHttpResponse) Request.post("http://localhost:11434/api/embeddings")
                .bodyString(body.toString(), ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();
        
        InputStream inputStream = response.getEntity().getContent();

        String jsonResponse = EntityUtils.toString(response.getEntity());

        System.out.println("FULL RESPONSE LENGTH: " + jsonResponse.length());
        System.out.println(jsonResponse);       
        
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray arr = json.getJSONArray("embedding");

        List<Double> embedding = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            embedding.add(arr.getDouble(i));
        }

        return embedding;
    }
}
