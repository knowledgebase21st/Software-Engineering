/**
 * Utility methods for Singly Linked List examples.
 *
 * This class contains commonly used operations that are shared
 * across multiple Linked List examples.
 *
 * Examples:
 * - Traverse Linked List
 * - Count Nodes
 * - Search Node
 * - Find Middle Node
 * - Reverse Linked List
 *
 * Author: Sanjay Ghosh
 */
public final class LinkedListUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private LinkedListUtils() {
    }

    /**
     * Prints all nodes in the linked list.
     *
     * Example Output:
     * 10 -> 20 -> 30 -> null
     *
     * Time Complexity: O(n)
     *
     * @param head Head node of the linked list.
     */
    public static void printList(Node head) {

        Node current = head;

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }

        System.out.println("null");
    }

    /**
     * Counts the number of nodes in the linked list.
     *
     * Time Complexity: O(n)
     *
     * @param head Head node.
     * @return Number of nodes.
     */
    public static int countNodes(Node head) {

        int count = 0;

        Node current = head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    /**
     * Searches for a value in the linked list.
     *
     * Time Complexity: O(n)
     *
     * @param head Head node.
     * @param value Value to search.
     * @return true if found; otherwise false.
     */
    public static boolean search(Node head, int value) {

        Node current = head;

        while (current != null) {

            if (current.data == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Returns the middle node using the
     * Fast Pointer / Slow Pointer technique.
     *
     * Time Complexity: O(n)
     *
     * @param head Head node.
     * @return Middle node.
     */
    public static Node findMiddle(Node head) {

        if (head == null) {
            return null;
        }

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

        }

        return slow;
    }

    /**
     * Reverses the linked list.
     *
     * Time Complexity: O(n)
     *
     * @param head Head node.
     * @return New head after reversal.
     */
    public static Node reverse(Node head) {

        Node previous = null;
        Node current = head;
        Node next;

        while (current != null) {

            next = current.next;

            current.next = previous;

            previous = current;

            current = next;
        }

        return previous;
    }

    /**
     * Inserts a node at the beginning.
     *
     * Time Complexity: O(1)
     *
     * @param head Current head.
     * @param data Value to insert.
     * @return New head.
     */
    public static Node insertAtBeginning(Node head, int data) {

        Node newNode = new Node(data);

        newNode.next = head;

        return newNode;
    }

    /**
     * Inserts a node at the end.
     *
     * Time Complexity: O(n)
     *
     * @param head Current head.
     * @param data Value to insert.
     * @return Head node.
     */
    public static Node insertAtEnd(Node head, int data) {

        Node newNode = new Node(data);

        if (head == null) {
            return newNode;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;

        return head;
    }

    /**
     * Deletes the first occurrence of a value.
     *
     * Time Complexity: O(n)
     *
     * @param head Head node.
     * @param value Value to delete.
     * @return Updated head.
     */
    public static Node deleteNode(Node head, int value) {

        if (head == null) {
            return null;
        }

        if (head.data == value) {
            return head.next;
        }

        Node current = head;

        while (current.next != null &&
               current.next.data != value) {

            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }

        return head;
    }

    /**
     * Detects whether the linked list contains a loop
     * using Floyd's Cycle Detection Algorithm.
     *
     * Time Complexity: O(n)
     *
     * @param head Head node.
     * @return true if loop exists.
     */
    public static boolean hasLoop(Node head) {

        Node slow = head;
        Node fast = head;

        while (fast != null &&
               fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

}
