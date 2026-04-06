/* Author: Sanjay Ghosh
 * Copyright (c) 2026 Sanjay Ghosh
 * All rights reserved.
 * This is for example of how chunking could be done
 * */

import java.util.*;

public class ChunkHandler {

    public static void main (String[] args) {

	    String text="This is about oAuth\nand it will be chuncked,\nand it will be chunked,\nthere will be several sentences\nseveral lines, and it will go on \nand will be continued\nThis is for test only\nand it is not that big";
	    System.out.println("Doc=["+text+"]\n");
	    System.out.println("Fixed Chunk \n-----------------");
	    //List<String> cl_fixed = fixedChunk("This is about OAuth and it will be chunked, there will be several sentences",4);
	    List<String> cl_fixed = fixedChunk(text,4);
	    for (int i=0;i<cl_fixed.size();i++) {
	    	System.out.println("Chunk "+i+"= " + cl_fixed.get(i));
	    }

	    System.out.println("\n\nSemantic Chunk (By paragraph)\n-----------------");
	    List<String> cl_semantic =  semanticChunk(text);

	    for (int i=0;i<cl_semantic.size();i++) {
	    	System.out.println("Chunk "+i+"= " + cl_semantic.get(i));
	    }

	    System.out.println("\n\nOverlap Chunk \n-----------------");
	    List<String> cl_overlap =  chunkOverlap(text, 4,2);

	    for (int i=0;i<cl_overlap.size();i++) {
	    	System.out.println("Chunk "+i+"= " + cl_overlap.get(i));
	    }


    }
    public static List<String> fixedChunk(String text, int chunkSize) {
        String[] words = text.split("\\s+");
        List<String> chunks = new ArrayList<>();
    
        for (int i = 0; i < words.length; i += chunkSize) {
            StringBuilder chunk = new StringBuilder();
            for (int j = i; j < i + chunkSize && j < words.length; j++) {
                chunk.append(words[j]).append(" ");
            }
            chunks.add(chunk.toString().trim());
        }
        return chunks;
    }

    public static List<String> semanticChunk(String text) {
                return Arrays.asList(text.split("\\n\\n")); // split by paragraphs
    }

    public static List<String> chunkOverlap(String text, int chunkSize, int overlap) {
        String[] words = text.split("\\s+");
        List<String> chunks = new ArrayList<>();
    
        for (int i = 0; i < words.length; i += (chunkSize - overlap)) {
            StringBuilder chunk = new StringBuilder();
            for (int j = i; j < i + chunkSize && j < words.length; j++) {
                chunk.append(words[j]).append(" ");
            }
            chunks.add(chunk.toString().trim());
        }
        return chunks;
    }
}
