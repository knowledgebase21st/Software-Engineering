/**
 * Example:
 * Remove Duplicate Nodes from an unorted Linked List
 *
 * This example removes duplicate values from an unsorted
 * Singly Linked List.
 *
 * Time Complexity : O(n) , because HashSet is used, if HashSet is not used then O(n²)
 * Space Complexity: O(n) , if HashSet is not used then O(1)
 *
 * Author: Sanjay Ghosh
 */

import java.util.HashSet;

public class RemoveDuplicatesUnSortedExample {

    public static Node removeDuplicates(Node head) {

        if (head == null) {
            return null;
        }

        HashSet<Integer> seen = new HashSet<>();

        Node current = head;
        Node previous = null;

        while (current != null) {

            if (seen.contains(current.data)) {

                // Remove duplicate node
                previous.next = current.next;

            } else {

                seen.add(current.data);
                previous = current;

            }

            current = current.next;
        }

        return head;
    }

    public static void main(String[] args) {

        Node head = new Node(10);

        head.next = new Node(30);
        head.next.next = new Node(20);
        head.next.next.next = new Node(30);
        head.next.next.next.next = new Node(50);
        head.next.next.next.next.next = new Node(40);
        head.next.next.next.next.next.next = new Node(50);
        head.next.next.next.next.next.next.next = new Node(50);

        System.out.println("Before");

        LinkedListUtils.printList(head);

        head = removeDuplicates(head);

        System.out.println();

        System.out.println("After");

        LinkedListUtils.printList(head);
    }
}
