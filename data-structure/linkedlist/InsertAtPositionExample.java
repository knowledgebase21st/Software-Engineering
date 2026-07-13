/**
 * Example:
 * Insert a Node at a Specific Position
 *
 * This example demonstrates how to insert a new node
 * at a specified position in a Singly Linked List.
 *
 * Time Complexity : O(n)
 * Space Complexity: O(1)
 *
 * Author: Sanjay Ghosh
 */
public class InsertAtPositionExample {

    /**
     * Inserts a node at the specified position.
     *
     * Position starts from 0.
     *
     * @param head Head node of the linked list
     * @param data Value to insert
     * @param position Position where node should be inserted
     * @return Updated head node
     */
    public static Node insertAtPosition(Node head,
                                        int data,
                                        int position) {

        Node newNode = new Node(data);

        /*
         * Insert at the beginning.
         */
        if (position == 0) {
            newNode.next = head;
            return newNode;
        }

        Node current = head;
        int index = 0;

        /*
         * Traverse until the node before
         * the desired position.
         */
        while (current != null &&
               index < position - 1) {

            current = current.next;
            index++;
        }

        /*
         * Invalid position.
         */
        if (current == null) {

            System.out.println("Invalid Position.");

            return head;
        }

        /*
         * Insert the new node.
         */
        newNode.next = current.next;
        current.next = newNode;

        return head;
    }

    public static void main(String[] args) {

        Node head = new Node(10);

        head.next = new Node(20);
        head.next.next = new Node(40);
        head.next.next.next = new Node(50);

        System.out.println("Before Insertion:");

        LinkedListUtils.printList(head);

        head = insertAtPosition(head,
                                30,
                                2);

        System.out.println();

        System.out.println("After Insertion:");

        LinkedListUtils.printList(head);
    }
}
