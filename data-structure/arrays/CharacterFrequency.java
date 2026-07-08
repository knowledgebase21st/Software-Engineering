/*
 * ============================================================================
 * Topic      : Arrays, Strings, HashMap and HashSet
 * File       : CharacterFrequency.java
 *
 * Description:
 * Demonstrates common Java programming problems including:
 *   - Character frequency using arrays -> Time Complexity O(n^2)
 *   
 *   - Character frequency using HashMap -> Time Complexity O(n)
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CharacterFrequency {

    public static void main(String[] args) {


        System.out.println("\n========== Character Frequency Using Array ==========");

        Set<String> frequency =
                countCharactersUsingArray("This is a test for the count of characters using array");

        System.out.println(frequency);

        System.out.println("\n========== Character Frequency Using HashMap ==========");

        Map<Character, Integer> map =
                countCharactersUsingHashMap("This is a test for the count of characters using array");

        System.out.println(map);
    }

    /**
     * Counts character frequency using nested loops.
     */
    private static Set<String> countCharactersUsingArray(String text) {

        List<String> frequencyList = new ArrayList<>();

        for (char currentChar : text.toCharArray()) {

            int count = 0;

            for (int i = 0; i < text.length(); i++) {

                if (currentChar == text.charAt(i)) {
                    count++;
                }
            }

            System.out.println(currentChar + " -> " + count);

            frequencyList.add(currentChar + " : " + count);
        }

        return new HashSet<>(frequencyList);
    }

    /**
     * Counts character frequency using a HashMap.
     */
    private static Map<Character, Integer>
    countCharactersUsingHashMap(String text) {

        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char currentChar : text.toCharArray()) {

            frequencyMap.put(currentChar,
                    frequencyMap.getOrDefault(currentChar, 0) + 1);
        }

        return frequencyMap;
    }
}
