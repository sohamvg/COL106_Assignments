package RedBlack;


// T is key, E is value
public class RBTree<T extends Comparable<T>, E> implements RBTreeInterface<T, E>  {

    private RedBlackNode<T, E> root;

    private static boolean inserted = false;

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

                return parentNode.left;

            } else if (parentNode.getKey().compareTo(key) < 0) {
                // parentNode.right = newNode;
                RedBlackNode<T, E> newNode = new RedBlackNode<>(key, ColorValue.RED, data);
                setRightChild(parentNode, newNode);

                return parentNode.right;

            } else {
                parentNode.addValue(data);

                return parentNode;
            }
        }

    }

/*
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
*/
    private void fixProblem(RedBlackNode<T,E> node) {

         //if (node.getKey().toString().equals("Chayan Malhotra")) System.out.println(node.getKey() + "  " + node.getColor() + "1111111111111111111111");
        // System.out.println(node.getKey().toString());

        if (node.isRoot()) { // node is root

            // if (node.getKey().toString().equals("Chayan Malhotra")) System.out.println(node.getKey() + "  " + node.getColor() + "000000000000000000000");

            node.setColor(ColorValue.BLACK);
            return;
        }


        else if (node.parent.isBlack()) {


            return;
        }



        RedBlackNode<T, E> parent = node.parent;
        if (parent.isRed()) {

            // RedBlackNode<T, E> uncle = node.getUncle();
            RedBlackNode<T, E> uncle = node.parent.getSibling();

            if (uncle != null && uncle.isRed()) {

                parent.setColor(ColorValue.BLACK);
                uncle.setColor(ColorValue.BLACK);
                RedBlackNode<T, E> grandParent = parent.parent;

                if (!grandParent.isRoot()) {

                    grandParent.setColor(ColorValue.RED);
                    fixProblem(grandParent);
                }
                else {
                    grandParent.setColor(ColorValue.BLACK);
                }
            }
            else { // uncle == null , null is taken black

                RedBlackNode<T, E> grandParent = parent.parent;


                // left left
                if (grandParent.left == parent && parent.left == null) {
                    //g.left = p.right;
                    setLeftChild(grandParent, parent.right);
                    //p.right = g;
                    setRightChild(parent, grandParent);
                    grandParent.setColor(ColorValue.RED);
                    parent.setColor(ColorValue.BLACK);
                }

                // left right
                else if (grandParent.left == parent && parent.right == node) {
                    //g.left = x.right;
                    setLeftChild(grandParent, node.right);
                    //p.right = x.left;
                    setRightChild(parent, node.left);
//            x.left = p;
//            x.right = g;
                    setLeftChild(node, parent);
                    setRightChild(node, grandParent);
                    node.setColor(ColorValue.BLACK);
                    grandParent.setColor(ColorValue.RED);

                }

                // right right
                else if (grandParent.right == parent && parent.right == node) {
                    setRightChild(grandParent, parent.left);
                    setLeftChild(parent, grandParent);
                    setRightChild(parent, node);

                    parent.setColor(ColorValue.BLACK);
                    grandParent.setColor(ColorValue.RED);
                }

                // right left
                else if (grandParent.right == parent && parent.left == node) {
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
    public void insert(T key, E value) {


        RedBlackNode<T, E> node = insertIterative(value, key);

        // if(!node.isRoot()) System.out.println(node.getKey() + " " + node.getColor() + "                " + node.parent.getKey() + " " + node.parent.getColor());
        // node.setColor(ColorValue.RED);
        // fixProblem(node);

        // if (node.getKey().toString().equals("Chayan Malhotra")) System.out.println(node.getKey() + "  " + node.parent.getColor());
    }

    @Override
    public RedBlackNode<T, E> search(T key) {
        return searcher(root, key);
    }

    private RedBlackNode<T, E> searcher(RedBlackNode<T, E> root, T key) { ////////////////////

        if (root == null) {
            // System.out.println("here 1");
            root = new RedBlackNode<>(null, null, null);
            root.setValue(null);
            return root;
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

//    }

}