/*
 * ============================================================================
 * Topic      : Big-O
 * File       : MissingNumberUsingHashSet.java
 *
 * Description:
 * Given an array containing numbers from 1 to N, where one number is missing, 
 * find the missing number.
 *
 * Check every number from 1 to N.
 * Store every element in a HashSet.
 * Then check numbers from 1 to N.
 * The first number that is not found is the missing number.
 *
 *
 * Time Complexity O(n)
 * Space Complexity O(n)
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

import java.util.HashSet;
import java.util.Set;

public class MissingNumberUsingHashSet {

    public static void main(String[] args) {

        int[] numbers = {1, 2, 3, 5, 6, 7, 8};

        int n = 8;

        Set<Integer> values = new HashSet<>();

        for (int number : numbers) {
            values.add(number);
        }

        for (int i = 1; i <= n; i++) {

            if (!values.contains(i)) {

                System.out.println("Missing Number : " + i);
                break;
            }
        }
    }
}
