public class MyHashTable_BST<K extends Comparable<K>, T> implements MyHashTable_<K, T>, Hash {
    private int hashSize;
    //private Object[] hashTable;
    private BST<T, K>[] hashTable;

    @SuppressWarnings("unchecked")
    MyHashTable_BST(int hashSize) {
        this.hashSize = hashSize;
        this.hashTable = new BST[hashSize];
    }

    @Override
    public int insert(K key, T obj) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        if (hashTable[(int) hashedKey] == null) {
            hashTable[(int) hashedKey] = new BST<T, K>();
        }
        boolean inserted = hashTable[(int) hashedKey].insertBST(obj, key);
        if (inserted) {
            return hashTable[(int) hashedKey].getCounter();
        }
        else {return -1;}
    }

    @Override
    public int update(K key, T obj) {
        boolean updated = false;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        BSTNode<T, K> b = hashTable[(int) hashedKey].searchBST(key);
        if (b != null) {
            updated = true;
            b.setData(obj);
            b.setKey(key);
        }
        if (updated) {
            return hashTable[(int) hashedKey].getCounter();
        }
        else return -1;
    }

    @Override
    public int delete(K key) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        boolean deleted = hashTable[(int) hashedKey].deleteBST(key);
        if (deleted) {
            return hashTable[(int) hashedKey].getCounter();
        }
        else return -1;
    }

    @Override
    public boolean contains(K key) {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        return hashTable[(int) hashedKey].searchBST(key) != null;
    }

    @Override
    public T get(K key) throws NotFoundException {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        BSTNode<T, K> b = hashTable[(int) hashedKey].searchBST(key);
        if (b != null) {
            return b.getData();
        }
        else throw new NotFoundException();
    }

    @Override
    public String address(K key) throws NotFoundException {
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        BSTNode<T, K> b = hashTable[(int) hashedKey].searchBST(key);
        if (b != null) {
            return hashedKey + "-" + hashTable[(int) hashedKey].getAddress();
        }
        else throw new NotFoundException();
    }
}
