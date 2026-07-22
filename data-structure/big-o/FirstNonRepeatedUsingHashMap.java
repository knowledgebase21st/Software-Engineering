/*
 * ============================================================================
 * Topic      : Big-O
 * File       : FirstNonRepeatedUsingHashMap.java
 *
 * Description:
 * Given a string, find the first character that appears only once.
 * Pass 1: Count the frequency of every character.
 * Pass 2: Traverse the string again. 
 * The first character whose frequency is 1 is the answer.
 * 
 *
 * Time Complexity O(n)
 * Space Complexity O(n)
 *
 * Example
 * Input
 *  Programming

 * Output
 * First Non-Repeated Character : P
 *
 *
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.HashMap;
import java.util.Map;

public class FirstNonRepeatedUsingHashMap {

    public static void main(String[] args) {

        String text = "Programming";

        Map<Character, Integer> frequencyMap = new HashMap<>();

        // First Pass - Count frequencies
        for (char ch : text.toCharArray()) {

            frequencyMap.put(ch,
                    frequencyMap.getOrDefault(ch, 0) + 1);
        }

        // Second Pass - Find first unique character
        boolean found = false;

        for (char ch : text.toCharArray()) {

            if (frequencyMap.get(ch) == 1) {

                System.out.println("First Non-Repeated Character : " + ch);

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No Non-Repeated Character Found.");
        }
    }
}
