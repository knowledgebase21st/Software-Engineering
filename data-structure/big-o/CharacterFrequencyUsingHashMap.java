/*
 * ============================================================================
 * Topic      : Big-O
 * File       : CharacterFrequencyUsingHashMap.java
 *
 * Description:
 * Given a string, count the frequency of each character.
 * Read each character once and store its frequency in a HashMap.
 *
 * Time Complexity O(n)
 * Space Complexity O(n)
 *
 * Example
 * Input
 *  Programming

 * Output
 *
 *  p -> 1
 *  r -> 2
 *  o -> 1
 *  g -> 2
 *  a -> 1
 *  m -> 2
 *  i -> 1
 *  n -> 1
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

public class CharacterFrequencyUsingHashMap {

    public static void main(String[] args) {

        String text = "Programming";

        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char ch : text.toCharArray()) {

            frequencyMap.put(ch,
                    frequencyMap.getOrDefault(ch, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {

            System.out.println(entry.getKey()
                    + " -> "
                    + entry.getValue());
        }
    }
}
