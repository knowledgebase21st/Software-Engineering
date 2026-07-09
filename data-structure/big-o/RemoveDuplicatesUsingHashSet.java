/*
 * ============================================================================
 * Topic      : Big-O
 * File       : RemoveDuplicatesUsingArray.java
 *
 * Description:
 * Given an integer array, remove all duplicate elements while 
 * preserving the order of their first occurrence.
 *
 * Store each element in a HashSet.
 * The add() method returns:
 *   true  → element is new
 *   false → duplicate already exists
 *
 * Time Complexity O(n)
 * Space Complexity O(n)
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
import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicatesUsingHashSet {

    public static void main(String[] args) {

        int[] numbers = {2, 5, 8, 5, 10, 2, 15, 8, 20};

        Set<Integer> uniqueNumbers = new HashSet<>();

        System.out.println("Array After Removing Duplicates:");

        for (int number : numbers) {

            if (uniqueNumbers.add(number)) {

                System.out.print(number + " ");
            }
        }
        System.out.println("");
    }
}
