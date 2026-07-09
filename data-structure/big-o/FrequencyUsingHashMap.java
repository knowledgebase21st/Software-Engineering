/*
 * ============================================================================
 * Topic      : Big-O 
 * File       : FrequencyUsingHashMap.java
 *
 * Description:
 * Given an integer array, count how many times each element appears.
 * HashMap is used 
 * Time Complexity o(n)
 *
 * Example
 * Input
 * 1 2 5 2 1 6 5 1

 * Output
 * 1 -> 3
 * 2 -> 2
 * 5 -> 2
 * 6 -> 1
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

public class FrequencyUsingHashMap {

    public static void main(String[] args) {

        int[] numbers = {1,2,5,2,1,6,5,1};

        Map<Integer,Integer> frequency = new HashMap<>();

        for(int value : numbers) {

            frequency.put(value,
                    frequency.getOrDefault(value,0)+1);
        }

        for(Map.Entry<Integer,Integer> entry
                : frequency.entrySet()) {

            System.out.println(entry.getKey()
                    + " -> "
                    + entry.getValue());
        }
    }

}
