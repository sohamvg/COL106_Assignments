public interface Hash {
    static long djb2(String str, int hashtableSize) {
        long hash = 5381;
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        if (hash < 0) {
            return (-hash) % hashtableSize;
        }
        return hash % hashtableSize;
    }
}
