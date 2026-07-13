/*
 * ============================================================================
 * Topic      : Big-O
 * File       : CountOccurrencesUsingArray.java
 *
 * Description:
 * Given an integer array and a target number, count how many times the target appears.
 *
 * Traverse the array. Whenever the target is found, increment the counter.
 *
 * Time Complexity O(n)
 * Space Complexity O(1)
 *
 * Example
 * Input
 *    Array  : 5 2 8 5 10 5 15 5
 *    Target : 5
 *
 * Output
 *    Occurrences of 5 : 4
 *
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

public class CountOccurrencesUsingArray {

    public static void main(String[] args) {

        int[] numbers = {5, 2, 8, 5, 10, 5, 15, 5};

        int target = 5;

        int count = 0;

        for (int number : numbers) {

            if (number == target) {
                count++;
            }
        }

        System.out.println("Occurrences of "
                + target + " : " + count);
    }
}
