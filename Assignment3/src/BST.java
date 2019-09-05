public class BST<T, K> implements Comparable<K> {
    BSTNode<T> root;
    private static int counter = 0;

    BST() {
        root = null;
    }

    int getCounter() {
        return counter;
    }

    void insertBST(T data)
    {
        counter = 1;
        root = insert(root, data);
    }

    private BSTNode<T> insert(BSTNode<T> root, T data) {
        if (root == null) {
            root = new BSTNode<>(data);
            return root;
        }
        if (root.compareTo(data) <= 0) {
            counter++;
            root.right = insert(root.right, data);
        }
        else {
            counter++;
            root.left = insert(root.left, data);
        }
        return root;
    }

    public BSTNode<T> searchBST(T data) {
        counter = 1;
        return search(root, data);
    }

    private BSTNode<T> search(BSTNode<T> root, T data) {
        if (root == null || root.getData().toString().equals(data.toString())) {
            return root;
        }
        else if (root.compareTo(data) <= 0) {
            counter++;
            return search(root.right, data);
        }
        counter++;
        return search(root.left, data);
    }

    @Override
    public int compareTo(K k) {
        return 0;
    }
}
