/*
 * ============================================================================
 * Topic      : Big-O
 * File       : TwoSumUsingArray.java
 *
 * Description:
 * Given an integer array and a target value, determine whether 
 * two numbers exist whose sum equals the target.
 *
 * Time Complexity O(n^2)
 * Space Complexity O(1)
 *
 * Example
 * Input
 *  Array  : 2 7 11 15 3 6
 *  Target : 9

 * Output
 *  Pair Found : 2 + 7 = 9
 *  Pair Found : 3 + 6 = 9
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

public class TwoSumUsingArray {

    public static void main(String[] args) {

        int[] numbers = {2, 7, 11, 15, 3, 6};
        int target = 9;

        boolean pairFound = false;

        for (int i = 0; i < numbers.length; i++) {

            for (int j = i + 1; j < numbers.length; j++) {

                if (numbers[i] + numbers[j] == target) {

                    System.out.println("Pair Found : "
                            + numbers[i] + " + "
                            + numbers[j] + " = "
                            + target);

                    pairFound = true;
                }
            }
        }

        if (!pairFound) {
            System.out.println("No Pair Found.");
        }
    }
}
