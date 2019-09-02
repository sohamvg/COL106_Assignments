public class MyHashTable<K, T> implements MyHashTable_<K, T> {


    @Override
    public int insert(K key, T obj) {
        return 0;
    }

    @Override
    public int update(K key, T obj) {
        return 0;
    }

    @Override
    public int delete(K key) {
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
