/*
 * ============================================================================
 * Topic      : Big-O
 * File       : CharacterFrequencyUsingArray.java
 *
 * Description:
 * Given a string, count the frequency of each character.
 * For every character, scan the remaining characters to count how many times it appears.
 *
 * Time Complexity O(n^2)
 * Space Complexity O(1)
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

public class CharacterFrequencyUsingArray {

    public static void main(String[] args) {

        String text = "Programming";

        boolean[] visited = new boolean[text.length()];

        for (int i = 0; i < text.length(); i++) {

            if (visited[i])
                continue;

            int count = 1;

            for (int j = i + 1; j < text.length(); j++) {

                if (text.charAt(i) == text.charAt(j)) {

                    count++;
                    visited[j] = true;
                }
            }

            System.out.println(text.charAt(i) + " -> " + count);
        }
    }
}
