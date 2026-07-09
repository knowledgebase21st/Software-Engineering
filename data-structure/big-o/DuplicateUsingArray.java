/*
 * ============================================================================
 * Topic      : Big-O
 * File       : DuplicateUsingArray.java
 *
 * Description:
 * Given an integer array, determine whether it contains any duplicate values.
 * For every element, compare it with every remaining element.
 *
 * Time Complexity O(n^2)
 * Space Complexity O(1)
 *
 * Example
 * Input
 *  {10, 25, 8, 30, 15, 25}

 * Output
 * Duplicate Found : 25
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

public class DuplicateUsingArray {

    public static void main(String[] args) {

        int[] numbers = {10, 25, 8, 30, 15, 25};

        boolean duplicateFound = false;

        for (int i = 0; i < numbers.length; i++) {

            for (int j = i + 1; j < numbers.length; j++) {

                if (numbers[i] == numbers[j]) {

                    System.out.println("Duplicate Found : " + numbers[i]);
                    duplicateFound = true;
                }
            }
        }

        if (!duplicateFound) {
            System.out.println("No Duplicate Found.");
        }
    }
}
