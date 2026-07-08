/*
 * ============================================================================
 * Topic      : Arrays, Strings, HashMap and HashSet
 * File       : AnagramExample.java
 *
 * Description:
 * Demonstrates common Java programming problems including:
 *   - Finding anagrams
 * Time Complexity m x n
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class AnagramExample {

    public static void main(String[] args) {

        System.out.println("========== Anagram Example ==========");

        List<String> states = new ArrayList<>();

        states.add("California");
        states.add("New York");
        states.add("New Orleans");
        states.add("Colorado");
        states.add("Arizona");
        states.add("New Mexico");
        states.add("Iowa");

        findMatchingAnagrams("naoariz", states);

    }

    /**
     * Finds all strings that are anagrams of the supplied word.
     */
    private static void findMatchingAnagrams(String word,
                                             List<String> candidates) {

        for (String candidate : candidates) {

            if (isAnagram(word, candidate)) {

                System.out.println(word +
                        " is an anagram of " +
                        candidate);
            }
        }
    }

    /**
     * Returns true if two strings are anagrams.
     */
    private static boolean isAnagram(String first,
                                     String second) {
    
        first = first.replaceAll("\\s", "").toLowerCase();
        second = second.replaceAll("\\s", "").toLowerCase();
    
        if (first.length() != second.length()) {
            return false;
        }
    
        int[] frequency = new int[26];
    
        for (int i = 0; i < first.length(); i++) {
    
            frequency[first.charAt(i) - 'a']++;
            frequency[second.charAt(i) - 'a']--;
    
        }
    
        for (int count : frequency) {
    
            if (count != 0) {
                return false;
            }
    
        }
        return true;
    }
}
