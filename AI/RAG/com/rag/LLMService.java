/* Author: Sanjay Ghosh*
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

import java.util.List;

public class LLMService {

    public static String generateAnswer(String query, List<String> docs) throws Exception {

        //String context = String.join("\n", contextList);
        // Join docs into a single string separated by newlines
        String context = String.join("\n", docs);

        JSONObject body = new JSONObject();
        body.put("model", "llama3");

        


        String prompt = """
        		Answer briefly in 2-3 sentences.

        		Context:
        		%s

        		Question:
        		%s
        		""".formatted(context, query);
        
        body.put("prompt", prompt);
        body.put("format", "json"); // optional but helps
        
        body.put("max_tokens", 150);
        body.put("temperature", 0.3);
        body.put("stream", false);

        ClassicHttpResponse response = (ClassicHttpResponse) Request.post("http://localhost:11434/api/generate")
                .bodyString(body.toString(), ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();

        String apiResponse = EntityUtils.toString(response.getEntity());

        System.out.println("RAW RESPONSE:\n" + apiResponse);

        try {
            JSONObject json = new JSONObject(apiResponse);
            return json.optString("response", "").trim();
        } catch (Exception e) {
            System.out.println("JSON parsing failed, returning raw response");
            return apiResponse; // fallback
    }
    }
}
