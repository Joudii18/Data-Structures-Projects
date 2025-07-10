/*
 * AVLTree.java
 * A generic AVL Tree implementation extending a Binary Search Tree (BST).
 * This class ensures that the tree remains balanced after insertions and deletions.
 */
public class AVLTree<T extends Comparable<? super T>> extends BST<T> {

    // Instance variable for storing the height of the tree
    protected int height;

    // Default constructor initializes an empty AVL tree with height -1
    public AVLTree() {
        super();
        height = -1;
    }

    // Constructor with a root node, height is still initialized to -1
    public AVLTree(BSTNode<T> root) {
        super(root);
        height = -1;
    }

    // Public method to get the height of the AVL tree
    public int getHeight() {
        return getHeight(root);
    }

    // Private helper method to calculate the height of a given node
    private int getHeight(BSTNode<T> node) {
        if (node == null) return -1;
        else return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // Helper method to get the left subtree as an AVL tree
    private AVLTree<T> getLeftAVL() {
        AVLTree<T> leftsubtree = new AVLTree<>(root.left);
        return leftsubtree;
    }

    // Helper method to get the right subtree as an AVL tree
    private AVLTree<T> getRightAVL() {
        AVLTree<T> rightsubtree = new AVLTree<>(root.right);
        return rightsubtree;
    }

    // Calculates the balance factor of the tree (difference between left and right subtree heights)
    protected int getBalanceFactor() {
        if (isEmpty()) return 0;
        else return getRightAVL().getHeight() - getLeftAVL().getHeight();
    }

    // Inserts a new element into the AVL tree and rebalances it
    public void insertAVL(String el, Student student) {
        super.insert(el, student); // Insert into the base BST
        this.balance(); // Rebalance the tree after insertion
    }

    // Deletes a specified element from the AVL tree and rebalances it
    public boolean deleteAVL(String el, Student student) {
        super.search(el).remove(student); // Remove the student from the BST node
        return true;
    }

    // Rebalances the AVL tree by checking balance factors and performing rotations
    protected void balance() {
        if (!isEmpty()) {
            getLeftAVL().balance(); // Balance the left subtree
            getRightAVL().balance(); // Balance the right subtree

            adjustHeight(); // Update the height of the current node

            int balanceFactor = getBalanceFactor();

            // Perform rotations based on balance factor
            if (balanceFactor == -2) {
                if (getLeftAVL().getBalanceFactor() < 0) rotateRight();
                else rotateLeftRight();
            } else if (balanceFactor == 2) {
                if (getRightAVL().getBalanceFactor() > 0) rotateLeft();
                else rotateRightLeft();
            }
        }
    }

    // Adjusts the height of the tree based on its children
    protected void adjustHeight() {
        if (isEmpty()) height = -1;
        else height = 1 + Math.max(getLeftAVL().getHeight(), getRightAVL().getHeight());
    }

    // Performs a right rotation on the AVL tree
    protected void rotateRight() {
        BSTNode<T> tempNode = root.right;
        root.right = root.left;
        root.left = root.right.left;
        root.right.left = root.right.right;
        root.right.right = tempNode;

        String val = root.key;
        root.key = root.right.key;
        root.right.key = val;

        getRightAVL().adjustHeight();
        adjustHeight();
    }

    // Performs a left rotation on the AVL tree
    protected void rotateLeft() {
        BSTNode<T> tempNode = root.left;
        root.left = root.right;
        root.right = root.left.right;
        root.left.right = root.left.left;
        root.left.left = tempNode;

        String val = root.key;
        root.key = root.left.key;
        root.left.key = val;

        getLeftAVL().adjustHeight();
        adjustHeight();
    }

    // Performs a left-right rotation (double rotation)
    protected void rotateLeftRight() {
        getLeftAVL().rotateLeft();
        getLeftAVL().adjustHeight();
        this.rotateRight();
        this.adjustHeight();
    }

    // Performs a right-left rotation (double rotation)
    protected void rotateRightLeft() {
        getRightAVL().rotateRight();
        getRightAVL().adjustHeight();
        this.rotateLeft();
        this.adjustHeight();
    }
}