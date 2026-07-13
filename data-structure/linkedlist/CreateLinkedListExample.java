/**
 * Example:
 * Create a Singly Linked List
 *
 * This example demonstrates:
 * 1. Creating nodes.
 * 2. Linking nodes together.
 * 3. Assigning the head node.
 *
 * Time Complexity:
 * Creating each node : O(1)
 * Linking nodes      : O(1)
 *
 * Author: Sanjay Ghosh
 */
public class CreateLinkedListExample {

    public static void main(String[] args) {

        Node first = new Node(10);
        Node second = new Node(20);
        Node third = new Node(30);

        first.next = second;
        second.next = third;

        Node head = first;

        System.out.println("Linked List created successfully.");

        System.out.println("Head Node   : " + head.data);
        System.out.println("Second Node : " + head.next.data);
        System.out.println("Third Node  : " + head.next.next.data);
    }
}
