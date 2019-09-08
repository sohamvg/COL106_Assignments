public class MyHashTable_BST<K extends Comparable<K>, T> implements MyHashTable_<K, T>, Hash {
    private int hashSize;
    private Object[] hashTable;

    MyHashTable_BST(int hashSize) {
        this.hashSize = hashSize;
        this.hashTable = new Object[hashSize];
    }

    @Override
    public int insert(K key, T obj) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        if (hashTable[(int) hashedKey] == null) {
            hashTable[(int) hashedKey] = new BST<T, K>();
        }
        ((BST<T, K>) hashTable[(int) hashedKey]).insertBST(obj, key);
        return ((BST<T, K>) hashTable[(int) hashedKey]).getCounter();
    }

    @Override
    public int update(K key, T obj) {
        boolean updated = false;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        BSTNode<T, K> b = ((BST<T, K>) hashTable[(int) hashedKey]).searchBST(key);
        if (b != null) {
            updated = true;
            b.setData(obj);
            b.setKey(key);
        }
        if (updated) {
            return ((BST<T, K>) hashTable[(int) hashedKey]).getCounter();
        }
        return -1;
    }

    @Override
    public int delete(K key) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        boolean deleted = ((BST<T, K>) hashTable[(int) hashedKey]).deleteBST(key);
        if (deleted) {
            return ((BST<T, K>) hashTable[(int) hashedKey]).getCounter();
        }
        return -1;
    }

    @Override
    public boolean contains(K key) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        return ((BST<T, K>) hashTable[(int) hashedKey]).searchBST(key) != null;
    }

    @Override
    public T get(K key) throws NotFoundException {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        BSTNode<T, K> b = ((BST<T, K>) hashTable[(int) hashedKey]).searchBST(key);
        if (b != null) {
            return b.getData();
        }
        throw new NotFoundException();
    }

    @Override
    public String address(K key) throws NotFoundException {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        BSTNode<T, K> b = ((BST<T, K>) hashTable[(int) hashedKey]).searchBST(key);
        if (b != null) {
            return hashedKey + "-" + ((BST<T, K>) hashTable[(int) hashedKey]).getAddress();
        }
        throw new NotFoundException();
    }
}
