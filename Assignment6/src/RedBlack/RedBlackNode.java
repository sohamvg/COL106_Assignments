package RedBlack;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {

    private T key;
    private E value;
    private ColorValue color;
    RedBlackNode<T, E> left, right, parent;

    RedBlackNode(T key, ColorValue color, E value, RedBlackNode<T, E> left, RedBlackNode<T, E> right, RedBlackNode<T, E> parent) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    void setColor(ColorValue color) {
        this.color = color;
    }

    T getKey() {
        return key;
    }

    boolean isRed() {
        return this.color.equals(ColorValue.RED);
    }

    boolean isBlack() {
        return this.color.toString().equals(ColorValue.BLACK.toString());
    }

    RedBlackNode<T, E> getSibling() {
        if (this.parent == null) {
            return null;
        }
        else if (this.parent.left == this) {
            return this.parent.right;
        }


        else return this.parent.left;

    }

    boolean isLeftChild() {
        assert !this.isRoot();
        return this.parent.left == this;
    }

//    boolean isRightChild() {
//        return this.parent.right == this;
//    }

//    public void setValue(List<E> value) {
//        this.value = value;
//    }

    boolean isRoot() {
        return this.parent == null;
    }

//    public ColorValue getColor() {
//        return color;
//    }
}
