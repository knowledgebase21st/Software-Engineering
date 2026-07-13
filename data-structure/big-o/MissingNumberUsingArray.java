/*
 * ============================================================================
 * Topic      : Big-O
 * File       : MissingNumberUsingArray.java
 *
 * Description:
 * Given an array containing numbers from 1 to N, where one number is missing, 
 * find the missing number.
 *
 * Check every number from 1 to N.
 * For each number, scan the entire array to see whether it exists.
 *
 * Time Complexity O(n²)
 * Space Complexity O(1)
 *
 * Example
 * Input
 *    1 2 3 5 6 7 8
 *
 * Output
 *    Missing Number : 4
 *
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

public class MissingNumberUsingArray {

    public static void main(String[] args) {

        int[] numbers = {1, 2, 3, 5, 6, 7, 8};

        int n = 8;

        boolean found;

        for (int i = 1; i <= n; i++) {

            found = false;

            for (int value : numbers) {

                if (value == i) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Missing Number : " + i);
                break;
            }
        }
    }
}
