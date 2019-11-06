package RedBlack;

public interface RBTreeInterface<T extends Comparable, E> {
    /**
     * Insert and element using the "key" as the key and the corresponding value.
     * Please note that value is a generic type and it can be anything.
     *
     * @param key
     * @param value
     */
    void insert(T key, E value);

    /**
     * Search using the key.
     *
     * @param key
     * @return node with key; null if does not exit
     */
    RedBlackNode<T, E> search(T key);

}
