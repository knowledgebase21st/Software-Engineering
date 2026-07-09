/*
 * ============================================================================
 * Topic      : Big-O
 * File       : GroupAnagramsUsingArray.java
 *
 * Description:
 * Given an array of strings, group all words that are anagrams.
 * Two words are anagrams if they contain the same letters in a different order.
 * For every word:
 *  Compare it with every remaining word.
 *  Sort both words.
 *  If the sorted strings are equal, they belong to the same group.
 *
 * Time Complexity O(n² × k log k)
 *                 n = number of words
 *                 k = average word length
 *                 Each pair of words requires sorting.
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

import java.util.Arrays;

public class GroupAnagramsUsingArray {

    public static void main(String[] args) {

        String[] words = {
                "listen",
                "silent",
                "eat",
                "tea",
                "bat",
                "ate"
        };

        boolean[] grouped = new boolean[words.length];

        for (int i = 0; i < words.length; i++) {

            if (grouped[i])
                continue;

            System.out.println("Group:");

            System.out.println(words[i]);

            grouped[i] = true;

            for (int j = i + 1; j < words.length; j++) {

                if (isAnagram(words[i], words[j])) {

                    System.out.println(words[j]);
                    grouped[j] = true;
                }
            }

            System.out.println();
        }
    }

    private static boolean isAnagram(String s1, String s2) {

        char[] a = s1.toCharArray();
        char[] b = s2.toCharArray();

        Arrays.sort(a);
        Arrays.sort(b);

        return Arrays.equals(a, b);
    }
}
