package RedBlack;

import Util.RBNodeInterface;

import java.util.ArrayList;
import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {

    private T key;
    private List<E> value = new ArrayList<>();
    private ColorValue color;
    RedBlackNode<T, E> left, right, parent;

    RedBlackNode(T key, ColorValue color, E value) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = color;
        this.value.add(value);
    }

    @Override
    public E getValue() {
        return value.get(0);
    }

    @Override
    public List<E> getValues() {
        return value;
    }

    void addValue(E value) {
        this.value.add(value);
    }

    void setColor(ColorValue color) {
        this.color = color;
    }

    T getKey() {
        return key;
    }

    boolean isRed() {
//        return this.color == ColorValue.RED;
        return this.color.toString().equals(ColorValue.RED.toString());
    }

    boolean isBlack() {
        return this.color.toString().equals(ColorValue.BLACK.toString());
    }

//    RedBlackNode<T, E> getUncle() {
//        if (this.parent == null || this.parent.parent == null) {
//            return null;
//        }
//        if (this.parent.parent.left.equals(this.parent)) {
//            return this.parent.parent.right;
//        }
//        else if (this.parent.parent.right == )
//    }

    RedBlackNode<T, E> getSibling() {
        if (this.parent == null) {
            return null;
        }
        else if (this.parent.left == this) {
            return this.parent.right;
        }


        else return this.parent.left;

    }

    public void setValue(List<E> value) {
        this.value = value;
    }

    boolean isRoot() {
        return this.parent == null;
    }

    public ColorValue getColor() {
        return color;
    }
}
