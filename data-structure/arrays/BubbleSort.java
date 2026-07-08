/*
 * ============================================================================
 * Topic      : Arrays
 * File       : BubbleSort.java
 *
 * Description:
 * Demonstrates the Bubble Sort algorithm.
 * Bubble Sort repeatedly compares adjacent elements and swaps them
 * until the array is sorted.
 *
 * Time Complexity:
 *   Best Case    : O(n)
 *   Average Case : O(n²)
 *   Worst Case   : O(n²)
 * Space Complexity:
 *   O(1)
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

        int[] numbers = {45, 12, 87, 23, 9, 56, 31};

        System.out.println("========== Original Array ==========");
        displayArray(numbers);

        bubbleSort(numbers);

        System.out.println("\n========== Sorted Array ==========");
        displayArray(numbers);

    }

    /**
     * Displays all elements of the array.
     */
    public static void displayArray(int[] array) {

        System.out.println(Arrays.toString(array));

    }

    /**
     * Sorts the array using Bubble Sort.
     */
    public static void bubbleSort(int[] array) {

        int n = array.length;

        for (int i = 0; i < n - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {

                if (array[j] > array[j + 1]) {

                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    swapped = true;
                }
            }

            // Stop if already sorted
            if (!swapped) {
                break;
            }
        }
    }

}
