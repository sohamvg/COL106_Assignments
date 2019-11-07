public class TraverseHashTable<K extends Comparable<K>, T> extends MyHashTable<K, T> {
    private MyArrayList<Integer> indexArray = new MyArrayList<>();

    TraverseHashTable(int hashSize) {
        super(hashSize);
    }

    @Override
    public void insert(K key, T obj) {
        int hashedKey = (int) Hash.djb2(key.toString(), super.getHashSize());
        indexArray.add(hashedKey);
        super.insert(key, obj);
    }

    public MyArrayList<Integer> getIndexArray() {
        return indexArray;
    }
}
