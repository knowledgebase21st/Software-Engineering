	/*
 * ============================================================================
 * Topic      : Arrays
 * File       : WorkOnArray.java
 *
 * Description:
 * Demonstrates common array interview problems including:
 *   - Frequency Count
 *   - Maximum Difference
 *   - Maximum Subarray Sum (Kadane's Algorithm)
 *   - Pair Sum (Two Sum)
 *   - Duplicate Detection
 *   - Move Zeros
 *   - Remove Duplicates
 *   - Rotate Array
 *
 *   Time Complexity for each is O(n)
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.*;

public class WorkOnArray {

    public static void main(String[] args) {

        System.out.println("========== Frequency ==========");
        int[] frequencyArray = {1,2,5,2,1,6,5,1,1,1};
        getFrequency(frequencyArray);

        System.out.println("\n========== Maximum Difference ==========");
        int[] diffArray = {7,6,8,9,-2,-5,11,7,5};
        System.out.println("Maximum Difference = " +
                findMaximumDifference(diffArray));

        System.out.println("\n========== Maximum SubArray (Kadane's Algorithm) ==========");
        int[] subArray = {7,6,8,9,-2,-5,11,7,5};
	System.out.println("Original Array Before");
        displayArray(subArray);
        System.out.println("Maximum Sum = " +
                maxSubArray(subArray));

        System.out.println("\n========== Pair Sum ==========");
        int[] pairArray = {1,3,6,2,7,15,3,-2,11,2};
        System.out.println("Number of pairs = " +
                countPairsWithSum(pairArray,13));

        System.out.println("\n========== Duplicate Detection ==========");
        int[] duplicateArray = {1,2,10,20,30,40,100,-1,-2,10};
        System.out.println("Contains Duplicate = " +
                containsDuplicate(duplicateArray));

        System.out.println("\n========== Move Zeros ==========");
        int[] zeroArray = {1,0,0,2,1,5,0,9};
        displayArray(moveZerosToEnd(zeroArray));

        System.out.println("\n========== Remove Duplicates ==========");
        int[] duplicateValues = {1,2,2,3,4,5,6,3,2,7};
        displayArray(removeDuplicates(duplicateValues));

        System.out.println("\n========== Rotate Array ==========");
        int[] rotateArray = {1,2,3,4,5,6,7,8};
	System.out.println("Original Array Before");
	displayArray(rotateArray);
	System.out.println("===============\n");
        displayArray(leftRotateArray(rotateArray,3));

    }

    /**
     * Prints an integer array.
     */
    public static void displayArray(int[] array){

        for(int value : array){
            System.out.print(value + " ");
        }

        System.out.println();
    }

    /**
     * Counts frequency of every element.
     */
    public static void getFrequency(int[] numbers){

        Map<Integer,Integer> frequencyMap = new HashMap<>();

        int maxFrequency = 0;
        int owner = 0;

        for(int number : numbers){

            int frequency =
                    frequencyMap.getOrDefault(number,0)+1;

            frequencyMap.put(number,frequency);

            if(frequency > maxFrequency){
                maxFrequency = frequency;
                owner = number;
            }
        }

        System.out.println(frequencyMap);
        System.out.println("Highest Frequency : "
                + owner + " -> " + maxFrequency);

    }

    /**
     * Finds maximum difference.
     */
    public static int findMaximumDifference(int[] values){

        int minimum = Integer.MAX_VALUE;
        int maximum = Integer.MIN_VALUE;

        for(int value : values){

            minimum = Math.min(minimum,value);
            maximum = Math.max(maximum,value);

        }

        return maximum-minimum;

    }

    /**
     * Kadane's Algorithm.
     */
    public static int maxSubArray(int[] numbers){

        int current = numbers[0];
        int maximum = numbers[0];

        for(int i=1;i<numbers.length;i++){

            current =
                    Math.max(numbers[i],
                            current + numbers[i]);

            maximum =
                    Math.max(maximum,current);

        }

        return maximum;

    }

    /**
     * Counts number of pairs whose sum equals target.
     */
    public static int countPairsWithSum(int[] array,
                                        int target){

        int count=0;

        Set<Integer> set = new HashSet<>();

        for(int value : array){

            int difference = target-value;

            if(set.contains(difference)){

                System.out.println(value
                        +" + "
                        +difference
                        +" = "
                        +target);

                count++;

            }

            set.add(value);

        }

        return count;

    }

    /**
     * Detects duplicate values.
     */
    public static boolean containsDuplicate(int[] array){

        Set<Integer> set = new HashSet<>();

        for(int value : array){

            if(set.contains(value))
                return true;

            set.add(value);

        }

        return false;

    }

    /**
     * Moves zeros to the end.
     */
    public static int[] moveZerosToEnd(int[] array){

        int[] output = new int[array.length];

        int index=0;

        for(int value : array){

            if(value!=0){

                output[index++] = value;

            }

        }

        return output;

    }

    /**
     * Removes duplicate values.
     */
    public static int[] removeDuplicates(int[] array){

        int[] output = new int[array.length];

        int index=0;

        Set<Integer> set = new HashSet<>();

        for(int value : array){

            if(!set.contains(value)){

                output[index++] = value;
                set.add(value);

            }

        }

        return output;

    }

    /**
     * Left rotates an array by k positions.
     */
    public static int[] leftRotateArray(int[] array,
                                        int k){

        int length = array.length;

        int[] rotated = new int[length];

        int start = length-k;

        for(int i=0;i<length-k;i++){

            rotated[i]=array[i+k];

            if(start<length){

                rotated[start]=array[i];
                start++;

            }

        }

        return rotated;

    }

}
