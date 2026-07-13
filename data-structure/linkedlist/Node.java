/**
 * Represents a node in a Singly Linked List.
 *
 * Each node contains:
 * 1. The data.
 * 2. A reference to the next node.
 *
 * Author: Sanjay Ghosh
 */
public class Node {

    int data;
    Node next;

    /**
     * Creates a node with the specified data.
     *
     * @param data value to be stored in the node
     */
    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}
