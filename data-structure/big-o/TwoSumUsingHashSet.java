/*
 * ============================================================================
 * Topic      : Big-O
 * File       : TwoSumUsingHashSet.java
 *
 * Description:
 * Given an integer array and a target value, determine whether 
 * two numbers exist whose sum equals the target.
 *
 * Time Complexity O(n)
 * Space Complexity O(n)
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

import java.util.HashSet;
import java.util.Set;

public class TwoSumUsingHashSet {

    public static void main(String[] args) {

        int[] numbers = {2, 7, 11, 15, 3, 6};
        int target = 9;

        Set<Integer> visited = new HashSet<>();

        boolean pairFound = false;

        for (int number : numbers) {

            int complement = target - number;

            if (visited.contains(complement)) {

                System.out.println("Pair Found : "
                        + complement + " + "
                        + number + " = "
                        + target);

                pairFound = true;
            }

            visited.add(number);
        }

        if (!pairFound) {
            System.out.println("No Pair Found.");
        }
    }
}
