/************************  BST.java  **************************
 *                 Generic binary search tree with LinkedList in nodes
 */

import java.util.LinkedList;

/**
 * Binary Search Tree (BST) class to manage nodes containing linked lists of students.
 */
public class BST<T extends Comparable<? super T>> {

    // Root node of the BST
    protected BSTNode<T> root = null;

    /**
     * Default constructor initializing an empty BST.
     */
    public BST() {
    }

    /**
     * Constructor to initialize the BST with a specific root node.
     */
    public BST(BSTNode<T> root) {
        this.root = root;
    }

    /**
     * Checks if the BST is empty.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Inserts a new student into the BST based on the given key.
     * If the key already exists, the student is added to the existing node's list.
     */
    public void insert(String key, Student student) {
        root = insertRec(root, key, student);
    }

    /**
     * Recursive helper method for insertion.
     */
    protected BSTNode<T> insertRec(BSTNode<T> node, String key, Student student) {
        // Base case: If node is null, create a new node with the student.
        if (node == null) {
            return new BSTNode<>(key, student);
        }

        // If the key matches, add the student to the node's list if not already present.
        if (key.compareTo(node.key) == 0) {
            if (!node.Students.contains(student)) {
                node.Students.add(student);
            }
        }
        // If the key is smaller, recurse on the left child.
        else if (key.compareTo(node.key) < 0) {
            node.left = insertRec(node.left, key, student);
        }
        // If the key is larger, recurse on the right child.
        else {
            node.right = insertRec(node.right, key, student);
        }

        return node;
    }

    /**
     * Searches for a node by key and returns the associated list of students.
     */
    public LinkedList<Student> search(String key) {
        BSTNode<T> node = root; // Start at the root.

        // Traverse the tree to find the key.
        while (node != null) {
            if (key.compareTo(node.key) == 0) {
                return node.Students;
            } else if (key.compareTo(node.key) < 0) {
                node = node.left; // Search in the left subtree.
            } else {
                node = node.right; // Search in the right subtree.
            }
        }

        // Return an empty list if the key is not found.
        return new LinkedList<>();
    }
}
