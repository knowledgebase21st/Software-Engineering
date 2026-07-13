/**
 * Example:
 * Merge Two Sorted Linked Lists
 *
 * This example demonstrates how to merge two sorted
 * linked lists into a single sorted linked list.
 *
 * Time Complexity : O(n + m)
 * Space Complexity: O(1)
 *
 * Author: Sanjay Ghosh
 */
public class MergeSortedLinkedListsExample {

    /**
     * Merges two sorted linked lists.
     *
     * @param list1 Head of first sorted list
     * @param list2 Head of second sorted list
     * @return Head of merged sorted list
     */
    public static Node merge(Node list1, Node list2) {

        /*
         * Dummy node simplifies the merge logic.
         */
        Node dummy = new Node(0);

        Node tail = dummy;

        while (list1 != null && list2 != null) {

            if (list1.data <= list2.data) {

                tail.next = list1;
                list1 = list1.next;

            } else {

                tail.next = list2;
                list2 = list2.next;
            }

            tail = tail.next;
        }

        /*
         * Append remaining nodes.
         */
        if (list1 != null) {
            tail.next = list1;
        }

        if (list2 != null) {
            tail.next = list2;
        }

        return dummy.next;
    }

    public static void main(String[] args) {

        /*
         * First Sorted List
         */
        Node list1 = new Node(10);
        list1.next = new Node(30);
        list1.next.next = new Node(50);

        /*
         * Second Sorted List
         */
        Node list2 = new Node(20);
        list2.next = new Node(40);
        list2.next.next = new Node(60);

        System.out.println("List 1");

        LinkedListUtils.printList(list1);

        System.out.println();

        System.out.println("List 2");

        LinkedListUtils.printList(list2);

        Node merged = merge(list1, list2);

        System.out.println();

        System.out.println("Merged List");

        LinkedListUtils.printList(merged);
    }
}
