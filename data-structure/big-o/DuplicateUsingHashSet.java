/*
 * ============================================================================
 * Topic      : Big-O
 * File       : DuplicateUsingHashSet.java
 *
 * Description:
 * Given an integer array, determine whether it contains any duplicate values.
 * Store every element in a HashSet.
 * If an element already exists, it is a duplicate.
 *
 * Time Complexity O(n^2)
 * Space Complexity O(n)
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

import java.util.HashSet;
import java.util.Set;

public class DuplicateUsingHashSet {

    public static void main(String[] args) {

        int[] numbers = {10, 25, 8, 30, 15, 25};

        Set<Integer> visited = new HashSet<>();

        boolean duplicateFound = false;

        for (int number : numbers) {

            if (visited.contains(number)) {

                System.out.println("Duplicate Found : " + number);
                duplicateFound = true;
                break;
            }

            visited.add(number);
        }

        if (!duplicateFound) {
            System.out.println("No Duplicate Found.");
        }
    }
}
