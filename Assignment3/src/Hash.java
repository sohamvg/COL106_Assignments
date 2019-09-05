import java.lang.Math;

public interface Hash {
    static long djb2(String str, int hashtableSize) {
        long hash = 5381;
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        return Math.abs(hash) % hashtableSize;
    }

    static long sdbm(String str, int hashtableSize) {
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return Math.abs(hash) % (hashtableSize - 1) + 1;
    }
}
