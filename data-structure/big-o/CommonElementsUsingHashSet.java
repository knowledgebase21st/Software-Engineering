/*
 * ============================================================================
 * Topic      : Big-O
 * File       : CommonElementsUsingHashSet.java
 *
 * Description:
 * Given two integer arrays, find the common elements.
 * Store all elements of the first array in a HashSet.
 * Then scan the second array.
 * If an element already exists in the set, it is common.
 *
 * Time Complexity O(n)
 * Space Complexity O(n)
 *
 * Example
 * Input
 *  Array 1 : 2 5 8 10 15 20
 *  Array 2 : 3 5 7 10 15 25

 * Output
 * Common Elements
 * 5
 * 10
 * 15
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

public class CommonElementsUsingHashSet {

    public static void main(String[] args) {

        int[] array1 = {2, 5, 8, 10, 15, 20};
        int[] array2 = {3, 5, 7, 10, 15, 25};

        Set<Integer> numbers = new HashSet<>();

        for (int value : array1) {
            numbers.add(value);
        }

        System.out.println("Common Elements:");

        boolean found = false;

        for (int value : array2) {

            if (numbers.contains(value)) {

                System.out.println(value);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No Common Elements.");
        }
    }
}
