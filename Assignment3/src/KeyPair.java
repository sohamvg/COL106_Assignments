public class KeyPair {
    String first, second;

    public KeyPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first+second;
    }
}