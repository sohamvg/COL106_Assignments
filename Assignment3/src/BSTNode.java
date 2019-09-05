public class BSTNode<T> implements Comparable<T>{
    BSTNode<T> left, right;
    private T data;

    BSTNode(T data) {
        this.left = null;
        this.right = null;
        this.data = data;
    }

    T getData() {
        return data;
    }

    /**
     * compares based on fname only
     * @param t
     * @return
     */
    @Override
    public int compareTo(T t) {
        return data.toString().compareTo(t.toString());
    }
    public int compareTo(T h) {
        return 0;
    }
}
