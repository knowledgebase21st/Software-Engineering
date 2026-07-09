/*
 * ============================================================================
 * Topic      : Big-O
 * File       : GroupAnagramsUsingArray.java
 *
 * Description:
 * Given an array of strings, group all words that are anagrams.
 * Two words are anagrams if they contain the same letters in a different order.
 * For every word:
 *  Instead of comparing every word with every other word,
 *  Create a canonical key by sorting each word.
 *
 *
 *  Example how does HashMap do it:
 *  listen
 *  ↓
 *  eilnst
 *
 *  silent
 *  ↓
 *  eilnst
 *  Both produce the same key.
 *  Use that key in a HashMap.
 *
 * Time Complexity O(n × k log k)
 *                 n = number of words
 *                 k = average word length
 * Space Complexity O(1)
 *
 * Example
 * Input
 *  listen
 *  silent
 *  eat
 *  tea
 *  bat
 *  ate

 * Output
 *   Group 1
 *      listen
 *      silent
 *   Group 2
 *      eat
 *      tea
 *      ate
 *   Group 3
 *      bat
 *
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.*;

public class GroupAnagramsUsingHashMap {

    public static void main(String[] args) {

        String[] words = {
                "listen",
                "silent",
                "eat",
                "tea",
                "bat",
                "ate"
        };

        Map<String, List<String>> groups = new HashMap<>();

        for (String word : words) {

            char[] letters = word.toCharArray();

            Arrays.sort(letters);

            String key = new String(letters);

            groups.computeIfAbsent(key,
                    k -> new ArrayList<>())
                    .add(word);
        }

        int groupNumber = 1;

        for (List<String> group : groups.values()) {

            System.out.println("Group " + groupNumber++);

            for (String word : group) {
                System.out.println(word);
            }

            System.out.println();
        }
    }
}
