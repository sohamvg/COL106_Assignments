package RedBlack;

// T is key, E is value
public class RBTree<T extends Comparable<T>, E> implements RBTreeInterface<T, E>  {

    private RedBlackNode<T, E> root;

    private final RedBlackNode<T, E> nullLeaf = new RedBlackNode<>(null,ColorValue.BLACK,null,null,null,null);

    public RBTree() {
        this.root = null;
    }

    private void setLeftChild(RedBlackNode<T, E> parent, RedBlackNode<T, E> child) {
        if (child != null) {
            child.parent = parent;
            parent.left = child;
        }
    }

    private void setRightChild(RedBlackNode<T, E> parent, RedBlackNode<T, E> child) {
        if (child != null) {
            child.parent = parent;
            parent.right = child;
        }
    }

    @Override
    public void insert(T key, E value) {

        RedBlackNode<T, E> node = insertIterative(value, key);
        fixProblem(node);
    }

    private RedBlackNode<T,E> insertIterative(E data, T key) {

        RedBlackNode<T,E> currentNode = this.root;
        RedBlackNode<T,E> parentNode = null;

        if (root == null) {
            root = new RedBlackNode<>(key, ColorValue.BLACK, data, nullLeaf, nullLeaf, null);
            return root;
        }

        else {
            while (currentNode.getKey() != null) {
                parentNode = currentNode;
                if (currentNode.getKey().compareTo(key) > 0) {
                    currentNode = currentNode.left;
                } else if (currentNode.getKey().compareTo(key) < 0) {
                    currentNode = currentNode.right;
                } else {
                    break;
                }
            }

            assert parentNode != null;
            if (parentNode.getKey().compareTo(key) > 0) {
                // parentNode.left = newNode;
                RedBlackNode<T, E> newNode = new RedBlackNode<>(key, ColorValue.RED, data, nullLeaf, nullLeaf, parentNode);
                setLeftChild(parentNode, newNode);

                return newNode;

            } else if (parentNode.getKey().compareTo(key) < 0) {
                // parentNode.right = newNode;
                RedBlackNode<T, E> newNode = new RedBlackNode<>(key, ColorValue.RED, data, nullLeaf, nullLeaf, parentNode);
                setRightChild(parentNode, newNode);

                return newNode;

            } else {
                    parentNode.setValue(data);

                return parentNode;
            }
        }

    }

    private void fixProblem(RedBlackNode<T,E> node) {

        if (node.isRoot()) { // node is root

            node.setColor(ColorValue.BLACK);
            return;
        }

        else if (node.parent.isBlack()) {
            return;
        }

        RedBlackNode<T, E> parent = node.parent;
        if (parent.isRed()) {

            RedBlackNode<T, E> uncle = node.parent.getSibling();

            if (uncle != null && uncle.isRed()) {

                parent.setColor(ColorValue.BLACK);
                uncle.setColor(ColorValue.BLACK);
                RedBlackNode<T, E> grandParent = parent.parent;

                if (!grandParent.isRoot()) {
                    //System.out.println(grandParent.getKey());

                    grandParent.setColor(ColorValue.RED);
                    fixProblem(grandParent);
                }

            }

            else { // uncle == null , null is taken black

                RedBlackNode<T, E> grandParent = parent.parent;

                // left left
                if (grandParent.left == parent && parent.left == node) {

                    if (grandParent.isRoot()) {
                        root = parent;
                        root.parent = null;
                    }

                    else if (grandParent.isLeftChild()) {
                        setLeftChild(grandParent.parent, parent);
                    }

                    else {
                        setRightChild(grandParent.parent, parent);
                    }
                    setLeftChild(grandParent, parent.right);
                    setRightChild(parent, grandParent);

                    grandParent.setColor(ColorValue.RED);
                    parent.setColor(ColorValue.BLACK);

                }

                // left right
                else if (grandParent.left == parent && parent.right == node) {

                    if (grandParent.isRoot()) {
                        root = node;
                        root.parent = null;
                    }

                    else if (grandParent.isLeftChild()) {
                        setLeftChild(grandParent.parent, node);
                    }

                    else {
                        setRightChild(grandParent.parent, node);
                    }
                    setLeftChild(grandParent, node.right);
                    setRightChild(parent, node.left);
                    setLeftChild(node, parent);
                    setRightChild(node, grandParent);

                    node.setColor(ColorValue.BLACK);
                    grandParent.setColor(ColorValue.RED);

                }

                // right right
                else if (grandParent.right == parent && parent.right == node) {

                    if (grandParent.isRoot()) {
                        root = parent;
                        root.parent = null;
                    }

                    else if (grandParent.isLeftChild()) {
                        setLeftChild(grandParent.parent, parent);
                    }

                    else {
                        setRightChild(grandParent.parent, parent);
                    }

                    setRightChild(grandParent, parent.left);
                    setLeftChild(parent, grandParent);
                    setRightChild(parent, node);

                    parent.setColor(ColorValue.BLACK);
                    grandParent.setColor(ColorValue.RED);

                }

                // right left
                else if (grandParent.right == parent && parent.left == node) {

                    if (grandParent.isRoot()) {
                        root = node;
                        root.parent = null;
                    }

                    else if (grandParent.isLeftChild()) {
                        setLeftChild(grandParent.parent, node);
                    }

                    else {
                        setRightChild(grandParent.parent, node);
                    }

                    setRightChild(grandParent, node.left);
                    setLeftChild(parent, node.right);
                    setLeftChild(node, grandParent);
                    setRightChild(node, parent);

                    node.setColor(ColorValue.BLACK);
                    grandParent.setColor(ColorValue.RED);

                }

            }
        }

    }


    @Override
    public RedBlackNode<T, E> search(T key) {
        return searcher(root, key);
    }

    private RedBlackNode<T, E> searcher(RedBlackNode<T, E> root, T key) {

        if (root.getKey() == null) {
            return root;
        }

        else if (root.getKey().compareTo(key) > 0) {

            return searcher(root.left, key);
        }
        else {
            if (root.getKey().compareTo(key) == 0) {
                return root;
            }
            else {
                return searcher(root.right, key);

            }
        }

    }

}