public class KeyPair<X, Y> implements Comparable<KeyPair<X, Y>> {
    private X first;
    private Y second;

    KeyPair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public X getFirst() {
        return first;
    }

    @Override
    public String toString() {
        return first + "" + second;
    }

    @Override
    public int compareTo(KeyPair<X, Y> xyKeyPair) {
        return this.first.toString().compareTo(xyKeyPair.getFirst().toString());
    }
}