/*
 * ============================================================================
 * Topic      : Big-O
 * File       : RemoveDuplicatesUsingArray.java
 *
 * Description:
 * Given an integer array, remove all duplicate elements while 
 * preserving the order of their first occurrence.
 *
 * For every element, check whether it has already appeared before it.
 * If it has not, print it.
 *
 * Time Complexity O(n^2)
 * Space Complexity O(1)
 *
 * Example
 * Input
 *  2 5 8 5 10 2 15 8 20

 * Output
 *  2 5 8 10 15 20
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

public class RemoveDuplicatesUsingArray {

    public static void main(String[] args) {

        int[] numbers = {2, 5, 8, 5, 10, 2, 15, 8, 20};

        System.out.println("Array After Removing Duplicates:");

        for (int i = 0; i < numbers.length; i++) {

            boolean duplicate = false;

            for (int j = 0; j < i; j++) {

                if (numbers[i] == numbers[j]) {

                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                System.out.print(numbers[i] + " ");
            }
        }
        System.out.println("");
    }
}
