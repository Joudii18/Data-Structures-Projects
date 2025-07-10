import java.util.LinkedList;

/**
 * Represents a node in a Binary Search Tree (BST) that stores a key and a linked list of students.
 */
public class BSTNode<T extends Comparable<? super T>> {

    // Key used for sorting nodes in the BST
    protected String key;

    // List of students associated with this key
    protected LinkedList<Student> Students;

    // References to the left and right child nodes
    protected BSTNode<T> left, right;

    // Height of the node, used for AVL balancing or other tree operations
    protected int height;

    /**
     * Constructor to create a new node with a given key and student.
     * Initializes the student list and sets the node as a leaf with height 0.
     */
    public BSTNode(String key, Student student) {
        this.key = key;
        this.Students = new LinkedList<>(); // Initialize the list of students
        this.Students.add(student);        // Add the first student to the list
        this.left = null;                  // No left child initially
        this.right = null;                 // No right child initially
        this.height = 0;                   // New nodes are leaf nodes with height 0
    }
}
