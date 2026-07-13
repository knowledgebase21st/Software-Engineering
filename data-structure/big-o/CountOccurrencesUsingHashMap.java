/*
 * ============================================================================
 * Topic      : Big-O
 * File       : CountOccurrencesUsingHashMap.java
 *
 * Description:
 * Given an integer array and a target number, count how many times the target appears.
 *
 * Store the frequency of every element in a HashMap.
 * Then simply retrieve the count of the required element.
 *
 * Time Complexity O(n)
 * Space Complexity O(n)
 *
 * Example
 * Input
 *    Array  : 5 2 8 5 10 5 15 5
 *    Target : 5
 *
 * Output
 *    Occurrences of 5 : 4
 *
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.HashMap;
import java.util.Map;

public class CountOccurrencesUsingHashMap {

    public static void main(String[] args) {

        int[] numbers = {5, 2, 8, 5, 10, 5, 15, 5};

        int target = 5;

        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int number : numbers) {

            frequencyMap.put(number,
                    frequencyMap.getOrDefault(number, 0) + 1);
        }

        int count = frequencyMap.getOrDefault(target, 0);

        System.out.println("Occurrences of "
                + target + " : " + count);
    }
}
