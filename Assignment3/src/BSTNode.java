public class BSTNode<T, K> {
    BSTNode<T, K> left, right;
    private T data;
    private K key;

    BSTNode(T data, K key) {
        this.left = null;
        this.right = null;
        this.data = data;
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }

//    @Override
//    public int compareTo(K k) {
//        return this.key.compareTo(k);
//    }

    /**
     * compares based on fname only
     * @param t
     * @return
     */

//    @Override
//    public boolean equals(Object o) {
//        if (data.equals(o)) return true;
//        if (this == null || o == null || getClass() != o.getClass()) return false;
//        return false;
//    }
}
