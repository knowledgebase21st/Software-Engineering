/*
 * ============================================================================
 * Topic      : Java Collections - ArrayList
 * File       : ArrayListExample.java
 *
 * Description:
 * Demonstrates common ArrayList operations including:
 *   - Creating an ArrayList
 *   - Adding elements
 *   - Inserting at a specific index
 *   - Updating elements
 *   - Removing elements
 *   - Searching
 *   - Iterating
 *   - Sorting
 *
 * GitHub:
 * https://github.com/knowledgebase21st/Software-Engineering
 *
 * Author:
 * Sanjay Ghosh
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Collections;

public class ArrayListExample {

    public static void main(String[] args) {

        ArrayList<String> cities = new ArrayList<>();

        // Add elements
        cities.add("Chicago");
        cities.add("New York");
        cities.add("Dallas");
        cities.add("San Francisco");
        cities.add("Sioux City");
        cities.add("Las Vegas");

        System.out.println("========== Original List ==========");
        System.out.println(cities);

        // Insert an element at a specific position
        cities.add(2, "Denver");

        System.out.println("\n========== After Inserting Denver ==========");
        System.out.println(cities);

        // Update an element
        cities.set(3, "Boston");

        System.out.println("\n========== After Updating Index 3 ==========");
        System.out.println(cities);

        // Remove an element
        cities.remove("Dallas");

        System.out.println("\n========== After Removing Dallas ==========");
        System.out.println(cities);

        // Search
        System.out.println("\n========== Search ==========");
        System.out.println("Contains Boston? " + cities.contains("Boston"));

        // Size
        System.out.println("\n========== Size ==========");
        System.out.println("Number of Cities : " + cities.size());

        // Iterate
        System.out.println("\n========== Iterating ==========");
        for (String city : cities) {
            System.out.println(city);
        }

        // Sort
        Collections.sort(cities);

        System.out.println("\n========== Sorted List ==========");
        System.out.println(cities);
    }
}