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

    public MyArrayList<T> getHashList(K key) {
        int hashedKey = (int) Hash.djb2(key.toString(), hashSize);
        if (hashTable[hashedKey] == null) {
            return null;
        }
        return hashTable[hashedKey];
    }

//    public T get(K key) {
//        int hashedKey = (int) Hash.djb2(key.toString(), hashSize);
//        if (hashTable[hashedKey] == null) {
//            return null;
//        }
//        else {
//            Object[] ol = hashTable[hashedKey].getMyArrayList();
//            for (int i = 0; i < hashTable[hashedKey].size(); i++) {
//                if (ol[i].equals(key)) return (T) ol[i];
//            }
//        }
//        return null;
//    }

    public T get(K key) {
        MyArrayList<T> myArrayList = getHashList(key);
        if (myArrayList != null) {
            for (int i = 0; i < myArrayList.size(); i++) {
                T t = myArrayList.get(i);
                if (t.toString().equals(key.toString())) { // toString needed to remove unicode values.
                    return t;
                }
            }
        }
        return null;
    }

    public int getHashSize() {
        return hashSize;
    }
}
