public class MyHashTable_DH<K, T> implements MyHashTable_<K, T>, Hash {

    private int hashSize;
    private Object[] hashTable;
    MyHashTable_DH(int hashSize) {
        this.hashSize = hashSize;
        this.hashTable = new Object[hashSize];
    }

    @Override
    public int insert(K key, T obj) {
        int i = 0;
        boolean inserted = false;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        //System.out.println(key.toString() + " " + hashedKey);
        if (hashTable[(int) hashedKey] == null) {
            inserted = true;
            hashTable[(int) hashedKey] = obj;
        }
        else {
            while (i<hashSize) {
                i = i+1;
                long newHashedKey = (Hash.djb2(key.toString(), hashSize) + i*Hash.sdbm(key.toString(), hashSize))%hashSize;
                //System.out.println(key.toString() + " " + newHashedKey);
                if (hashTable[(int) newHashedKey] == null) {
                    inserted = true;
                    hashTable[(int) newHashedKey] = obj;
                    break;
                }
            }
        }
        if (inserted) {
            return i+1;
        }
        return -1;
    }

    @Override
    public int update(K key, T obj) {
        int i = 0;
        boolean updated = false;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        if (hashTable[(int) hashedKey] != null && hashTable[(int) hashedKey].equals(key)) {
            updated = true;
            hashTable[(int) hashedKey] = obj;
        }
        else {
            while (i<hashSize) {
                i = i+1;
                long newHashedKey = (Hash.djb2(key.toString(), hashSize) + i*Hash.sdbm(key.toString(), hashSize))%hashSize;
                if (hashTable[(int) newHashedKey] != null && hashTable[(int) newHashedKey].equals(key)) {
                    updated = true;
                    hashTable[(int) newHashedKey] = obj;
                    break;
                }
            }
        }
        if (updated) {
            return i+1;
        }
        return -1;
    }

    @Override
    public int delete(K key) {
        int i = 0;
        boolean deleted = false;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        if (hashTable[(int) hashedKey] != null && hashTable[(int) hashedKey].equals(key)) {
            deleted = true;
            hashTable[(int) hashedKey] = null;
        }
        else {
            while (i<hashSize) {
                i = i+1;
                long newHashedKey = (Hash.djb2(key.toString(), hashSize) + i*Hash.sdbm(key.toString(), hashSize))%hashSize;
                if (hashTable[(int) newHashedKey] != null && hashTable[(int) newHashedKey].equals(key)) {
                    deleted = true;
                    hashTable[(int) newHashedKey] = null;
                    break;
                }
            }
        }
        if (deleted) {
            return i+1;
        }
        return -1;
    }

    @Override
    public boolean contains(K key) {
        int i = 0;
        boolean found = false;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        //System.out.println(hashTable[(int) hashedKey].toString());
        if (hashTable[(int) hashedKey] != null && hashTable[(int) hashedKey].equals(key)) {
            found = true;
        }
        else {
            while (i<hashSize) {
                i = i+1;
                long newHashedKey = (Hash.djb2(key.toString(), hashSize) + i*Hash.sdbm(key.toString(), hashSize))%hashSize;
                if (hashTable[(int) hashedKey] != null && hashTable[(int) newHashedKey].equals(key)) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    @Override
    public T get(K key) throws NotFoundException {
        int i = 0;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        //System.out.println( " " + key.toString());
        if (hashTable[(int) hashedKey] != null && hashTable[(int) hashedKey].equals(key)) {
            return  (T) hashTable[(int) hashedKey];
        }
        else {
            while (i<hashSize) {
                i = i+1;
                long newHashedKey = (Hash.djb2(key.toString(), hashSize) + i*Hash.sdbm(key.toString(), hashSize))%hashSize;
                if (hashTable[(int) newHashedKey] != null && hashTable[(int) newHashedKey].equals(key)) {
                    return  (T) hashTable[(int) newHashedKey];
                }
            }
        }
        throw new NotFoundException();
    }

    @Override
    public String address(K key) throws NotFoundException {
        int i = 0;
        long hashedKey = Hash.djb2(key.toString(), hashSize);
        if (hashTable[(int) hashedKey] != null && hashTable[(int) hashedKey].equals(key)) {
            return Integer.toString((int) hashedKey);
        }
        else {
            while (i<hashSize) {
                i = i+1;
                long newHashedKey = (Hash.djb2(key.toString(), hashSize) + i*Hash.sdbm(key.toString(), hashSize))%hashSize;
                if (hashTable[(int) newHashedKey] != null && hashTable[(int) newHashedKey].equals(key)) {
                    return Integer.toString((int) newHashedKey);
                }
            }
        }
        throw new NotFoundException();
    }
}