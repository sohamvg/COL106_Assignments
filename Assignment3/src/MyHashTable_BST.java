public class MyHashTable_BST<K, T> implements MyHashTable_<K, T>, Hash {
    public int hashSize;
    public Object[] hashTable;

    public MyHashTable_BST(int hashSize) {
        this.hashSize = hashSize;
        this.hashTable = new Object[hashSize];
    }

    @Override
    public int insert(K key, T obj) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        if (hashTable[(int) hashedKey] == null) {
            hashTable[(int) hashedKey] = new BST<T>();
        }
        ((BST<T>) hashTable[(int) hashedKey]).insertBST(obj);
        return ((BST<T>) hashTable[(int) hashedKey]).getCounter();
    }

    @Override
    public int update(K key, T obj) {
        return 0;
    }

    @Override
    public int delete(K key) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        BSTNode<T> b = new BSTNode<T>((T) key);
        ((BST<T>) hashTable[(int) hashedKey]).searchBST(key);
        ((BST<T>) hashTable[(int) hashedKey]).getCounter();
        return 0;
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public T get(K key) throws NotFoundException {
        return null;
    }

    @Override
    public String address(K key) throws NotFoundException {
        return null;
    }
}
