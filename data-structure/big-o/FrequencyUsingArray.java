/*
 * ============================================================================
 * Topic      : Big-O. Arrays is used here.
 * File       : FrequencyUsingArray.java
 *
 * Description:
 * Given an integer array, count how many times each element appears.
 * Array is used and nested loop is used
 * Time Complexity n^2
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


public class FrequencyUsingArray {

    public static void main(String[] args) {

        int[] numbers = {1,2,5,2,1,6,5,1};

        boolean[] visited = new boolean[numbers.length];

        for(int i=0;i<numbers.length;i++) {

            if(visited[i])
                continue;

            int count = 1;

            for(int j=i+1;j<numbers.length;j++) {

                if(numbers[i]==numbers[j]) {
                    count++;
                    visited[j]=true;
                }
            }

            System.out.println(numbers[i] + " -> " + count);
        }
    }

}

