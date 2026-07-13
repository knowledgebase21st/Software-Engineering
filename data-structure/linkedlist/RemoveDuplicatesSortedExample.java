/**
 * Example:
 * Remove Duplicate Nodes from a Sorted Linked List
 *
 * This example removes duplicate values from a sorted
 * Singly Linked List.
 *
 * Time Complexity : O(n)
 * Space Complexity: O(1)
 *
 * Author: Sanjay Ghosh
 */

public class RemoveDuplicatesSortedExample {

    /**
     * Removes duplicate nodes from a sorted linked list.
     *
     * @param head Head node of the linked list.
     * @return Updated head node.
     */
    public static Node removeDuplicates(Node head) {

        if (head == null) {
            return null;
        }

        Node current = head;

        while (current != null &&
               current.next != null) {

            if (current.data == current.next.data) {

                /*
                 * Skip the duplicate node.
                 */
                current.next = current.next.next;

            } else {

                current = current.next;
            }
        }

        return head;
    }

    public static void main(String[] args) {

        Node head = new Node(10);

        head.next = new Node(20);
        head.next.next = new Node(20);
        head.next.next.next = new Node(30);
        head.next.next.next.next = new Node(30);
        head.next.next.next.next.next = new Node(40);
        head.next.next.next.next.next.next = new Node(50);
        head.next.next.next.next.next.next.next = new Node(50);

        System.out.println("Before Removing Duplicates");

        LinkedListUtils.printList(head);

        head = removeDuplicates(head);

        System.out.println();

        System.out.println("After Removing Duplicates");

        LinkedListUtils.printList(head);
    }
}
