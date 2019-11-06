// K is key, T is value
public class MyHashTable<K extends Comparable<K>, T> implements Hash {
    private int hashSize;
    private MyArrayList<T>[] hashTable;
    public int inserted = 0;

    MyHashTable(int hashSize) {
        this.hashSize = hashSize;
        this.hashTable = new MyArrayList[hashSize];
    }

    public void insert(K key, T obj) {
        int hashedKey = (int) Hash.djb2(key.toString(), hashSize);
        if (hashTable[hashedKey] == null) {
            hashTable[hashedKey] = new MyArrayList<>();
        }
        hashTable[hashedKey].add(obj);
        inserted+=1;
    }

    public Object[] getHashList(K key) {
        int hashedKey = (int) Hash.djb2(key.toString(), hashSize);
        if (hashTable[hashedKey] == null) {
            return null;
        }
        return hashTable[hashedKey].getMyArrayList();
    }
}
