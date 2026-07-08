/*
 * ============================================================================
 * Topic      : Arrays
 * File       : SecondLargest.java
 *
 * Description:
 * Demonstrates common array operations including:
 *   - Displaying an array
 *   - Finding the largest element
 *   - Finding the second largest element
 * Time Complexity:
 *   O(n)
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

public class SecondLargest {

    public static void main(String[] args) {

        int[] numbers = {45, 12, 87, 23, 9, 56, 31};

        System.out.println("========== Original Array ==========");
        displayArray(numbers);
        System.out.println("\nLargest Element       : " + findLargest(numbers));
        System.out.println("Second Largest Element: " + findSecondLargest(numbers));
    }

    /**
     * Displays all elements of the array.
     */
    public static void displayArray(int[] array) {

        System.out.println(Arrays.toString(array));

    }

    /**
     * Returns the largest element in the array.
     */
    public static int findLargest(int[] array) {

        int largest = Integer.MIN_VALUE;

        for (int value : array) {

            if (value > largest) {
                largest = value;
            }
        }

        return largest;
    }

    /**
     * Returns the second largest element in the array.
     */
    public static int findSecondLargest(int[] array) {

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int value : array) {

            if (value > largest) {

                secondLargest = largest;
                largest = value;

            } else if (value > secondLargest && value != largest) {

                secondLargest = value;
            }
        }

        return secondLargest;
    }

}
