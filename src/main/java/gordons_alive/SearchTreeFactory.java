package gordons_alive;

// Provides an object to fulfil BinaryTree or BinarySearchTree interfaces

public class SearchTreeFactory {
    public static BinaryTree newBinaryTree() {
        return new BinarySearchTreeObject();
    }
    public static BinarySearchTree newBinarySearchTree() {
        return new BinarySearchTreeObject();
    }
}
