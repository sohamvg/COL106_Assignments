package RedBlack;


public class RBTree<T extends Comparable<T>, E> implements RBTreeInterface<T, E>  {

    private RedBlackNode<T, E> root;

    private static boolean inserted = false;

    RBTree() {
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
        inserted = false;

        RedBlackNode<T, E> n = insertIterative(value, key);

//        if (inserted) {
//            resolveRed(n);
//        }

    }

    private RedBlackNode<T,E> insertIterative(E data, T key) {


        RedBlackNode<T,E> currentNode = this.root;
        RedBlackNode<T,E> parentNode = null;

        if (root == null) {
            // System.out.println("here here");
            root = new RedBlackNode<>(key, ColorValue.BLACK, data);
            // if (root.getValue() != null) {System.out.println(root.getValue());} else {System.out.println("hell");}
            return root;
        }

        else {
            while (currentNode != null) {
                parentNode = currentNode;
                if (currentNode.getKey().compareTo(key) > 0) {
                    currentNode = currentNode.left;
                } else if (currentNode.getKey().compareTo(key) < 0) {
                    currentNode = currentNode.right;
                } else {
                    break;
                }
            }

            if (parentNode.getKey().compareTo(key) > 0) {
                // parentNode.left = newNode;
                RedBlackNode<T, E> newNode = new RedBlackNode<>(key, ColorValue.RED, data);
                setLeftChild(parentNode, newNode);
                inserted = true;
                return parentNode.left;

            } else if (parentNode.getKey().compareTo(key) < 0) {
                // parentNode.right = newNode;
                RedBlackNode<T, E> newNode = new RedBlackNode<>(key, ColorValue.RED, data);
                setRightChild(parentNode, newNode);
                inserted = true;
                return parentNode.right;

            } else {
                parentNode.addValue(data);
                inserted = false;
                return parentNode;
            }
        }

    }


    private RedBlackNode<T, E> restructure(RedBlackNode<T, E> x) {
        RedBlackNode<T, E> p = x.parent;
        RedBlackNode<T, E> g = p.parent;

        // left left
        if (g.left == p && p.left == x) {
            //g.left = p.right;
            setLeftChild(g, p.right);
            //p.right = g;
            setRightChild(p, g);
            return p;
        }

        // left right
        else if (g.left == p && p.right == x) {
            //g.left = x.right;
            setLeftChild(g, x.right);
            //p.right = x.left;
            setRightChild(p, x.left);
//            x.left = p;
//            x.right = g;
            setLeftChild(x, p);
            setRightChild(x, g);
            return x;
        }

        // right right
        else if (g.right == p && p.right == x) {
            setRightChild(g, p.left);
            setLeftChild(p, g);
            setRightChild(p, x);
            return p;
        }

        // right left
        else if (g.right == p && p.left == x) {
            setRightChild(g, x.left);
            setLeftChild(p, x.right);
            setLeftChild(x, g);
            setRightChild(x, p);
            return x;
        }

        return x; // never
    }

    private void resolveRed(RedBlackNode<T,E> node) {

        if (node.isRoot()) { // node is root
            System.out.println("here root b");
            node.setColor(ColorValue.BLACK);
            return;
        }


        RedBlackNode<T, E> parent = node.parent;
        if (parent.isRed()) {

            RedBlackNode<T, E> uncle = node.getUncle();

            if (uncle != null && uncle.isRed()) {

                parent.setColor(ColorValue.BLACK);
                uncle.setColor(ColorValue.BLACK);
                RedBlackNode<T, E> grandParent = parent.parent;

                if (!grandParent.isRoot()) {
                    grandParent.setColor(ColorValue.RED);
                    resolveRed(grandParent);
                }
            }
            else {
                RedBlackNode<T, E> mid = restructure(node);
                mid.setColor(ColorValue.BLACK);
                mid.left.setColor(ColorValue.RED);
                mid.right.setColor(ColorValue.RED);
            }
        }
    }

//    private RedBlackNode<T, E> inserter(RedBlackNode<T, E> root, E data, T key) {
//        if (root == null) {
//            root = new RedBlackNode<>(key, ColorValue.BLACK, data);
//            return root;
//        }
//        else if (root.getKey().compareTo(key) > 0) {
//            if (root.left != null) {
//                root.left = inserter(root.left, data, key);
//                root.left.parent = root;
//            }
//            else {
////                 root.left = new RedBlackNode<T, E>(key, ColorValue.RED, data);
////                 root.left.parent = root;
//                RedBlackNode<T, E> newNode = new RedBlackNode<>(key, ColorValue.RED, data);
//
//                inserted = true;
//                insertedNode = newNode;
//                setLeftChild(root, newNode);
//
//                // resolveRed(root.left);
//            }
//        }
//        else if (root.getKey().compareTo(key) < 0) {
//
//                if (root.right != null) {
//                    root.right = inserter(root.right, data, key);
//                    root.right.parent = root;
//                } else {
//                    RedBlackNode<T, E> newNode = new RedBlackNode<>(key, ColorValue.RED, data);
//
//                    inserted = true;
//                    insertedNode = newNode;
//                    setRightChild(root, newNode);
//
//                    // resolveRed(root.right);
//                }
//
//        }
//        else { // root.getKey = key
//            root.addValue(data);
//        }
//
//        return root;
//    }


    @Override
    public RedBlackNode<T, E> search(T key) {
        return searcher(root, key);
    }

    private RedBlackNode<T, E> searcher(RedBlackNode<T, E> root, T key) { ////////////////////

        if (root == null) {
            // System.out.println("here 1");
            return null;
        }

        else if (root.getKey().compareTo(key) > 0) {

            return searcher(root.left, key);
        }
        else {
            if (root.getKey().compareTo(key) == 0) {
                //System.out.println("here alpha");
                return root;
            }
            else {
                return searcher(root.right, key);
            }
        }

    }


}