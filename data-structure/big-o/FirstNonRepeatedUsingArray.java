/*
 * ============================================================================
 * Topic      : Big-O
 * File       : FirstNonRepeatedUsingArray.java
 *
 * Description:
 * Given a string, find the first character that appears only once.
 * For every character, scan the entire string to determine how many times it appears.
 * If its count is 1, it is the first non-repeated character.
 *
 * Time Complexity O(n^2)
 * Space Complexity O(1)
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

public class FirstNonRepeatedUsingArray {

    public static void main(String[] args) {

        String text = "Programming";

        boolean found = false;

        for (int i = 0; i < text.length(); i++) {

            int count = 0;

            for (int j = 0; j < text.length(); j++) {

                if (text.charAt(i) == text.charAt(j)) {
                    count++;
                }
            }

            if (count == 1) {

                System.out.println("First Non-Repeated Character : "
                        + text.charAt(i));

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No Non-Repeated Character Found.");
        }
    }
}
