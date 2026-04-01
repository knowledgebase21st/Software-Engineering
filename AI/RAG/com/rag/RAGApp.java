/* Author: Sanjay Ghosh*
 * Copyright (c) 2026 Sanjay Ghosh
 * All rights reserved.
 * This is the main class where it stores and retrieves
 * */

package com.rag;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RAGApp {

    public static void main(String[] args) throws Exception {

        // Store sample data
        //StorageService.storeText("OAuth 2.0 is an authorization framework.");
        //StorageService.storeText("JWT is used for secure authentication.");
    	
    	/*
    	StorageService.storeText(
    		    "OAuth 2.0 is an authorization framework that allows applications to access user resources without sharing passwords. " +
    		    "It defines roles like Resource Owner, Client, Authorization Server, and Resource Server."
    		);
    	*/
    	StorageService.storeText(
    		    "RSA is a public key cryptographic algorithm used for secure data transmission. " +
    		    "It uses a pair of keys: a public key for encryption and a private key for decryption. " +
    		    "RSA is widely used in secure communications such as HTTPS."
    		);
    	
    	StorageService.storeText(
    		    "RSA is an algoritm " +
    		    "It is used in public key cryptography"
    		);


    	StorageService.storeText(
    		    "OAuth 2.0 uses flows like Authorization Code and Client Credentials to allow applications to get tokens. " +
    		    "Each flow defines how clients authenticate and obtain access tokens."
    		);

    	StorageService.storeText(
    		    "Access tokens issued by OAuth 2.0 can be validated by Resource Servers without contacting the Authorization Server each time. " +
    		    "This improves performance and reduces server load."
    		);
    		
 

    	//String query = "What is OAuth?";
    	String query = "Who are 3 scientists involved in RSA";

    	List<String> docs = Retriever.retrieve(query, 10);
    	docs = docs.stream().limit(3).toList();
    	
      System.out.println("Retrieved docs (size=" + docs.size() + "):");
        for (int i = 0; i < docs.size(); i++) {
            System.out.println(i + ": " + docs.get(i));
        }
        

        System.out.println("\n--- Retrieved Context ---");
        docs.forEach(System.out::println);

        String answer = LLMService.generateAnswer(query, docs);

        System.out.println("\n--- Final Answer ---");
        System.out.println(answer);
    }
    

}
