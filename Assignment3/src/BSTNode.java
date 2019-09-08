class BSTNode<T, K> {
    BSTNode<T, K> left, right;
    private T data;
    private K key;

    BSTNode(T data, K key) {
        this.left = null;
        this.right = null;
        this.data = data;
        this.key = key;
    }

    T getData() {
        return data;
    }

    void setData(T data) {
        this.data = data;
    }

    void setKey(K key) {
        this.key = key;
    }

    K getKey() {
        return key;
    }

}
