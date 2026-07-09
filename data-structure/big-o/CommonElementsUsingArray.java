/*
 * ============================================================================
 * Topic      : Big-O
 * File       : CommonElementsUsingArray.java
 *
 * Description:
 * Given two integer arrays, find the common elements.
 * Compare every element of the first array with every element of the second array.
 *
 * Time Complexity O(n^2)
 * Space Complexity O(1)
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

public class CommonElementsUsingArray {

    public static void main(String[] args) {

        int[] array1 = {2, 5, 8, 10, 15, 20};
        int[] array2 = {3, 5, 7, 10, 15, 25};

        System.out.println("Common Elements:");

        boolean found = false;

        for (int i = 0; i < array1.length; i++) {

            for (int j = 0; j < array2.length; j++) {

                if (array1[i] == array2[j]) {

                    System.out.println(array1[i]);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No Common Elements.");
        }
    }
}
