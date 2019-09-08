class BST<T, K extends Comparable<K>> {
    private BSTNode<T, K> root;
    private static int counter = 0;
    private static String address = "";
    private static boolean deleteError = false;

    BST() {
        root = null;
    }

    int getCounter() {
        return counter;
    }

    String getAddress() {
        return address;
    }

    void insertBST(T data, K key)
    {
        counter = 1;
        root = insert(root, data, key);
    }

    private BSTNode<T, K> insert(BSTNode<T, K> root, T data, K key) {
        if (root == null) {
            root = new BSTNode<>(data, key);
            return root;
        }
        if (root.getKey().compareTo(key) < 0) {
            counter++;
            root.right = insert(root.right, data, key);
        }
        else { // Node is inserted to left if first name is same
            counter++;
            root.left = insert(root.left, data, key);
        }
        return root;
    }

    BSTNode<T, K> searchBST(K key) {
        counter = 1;
        address = "";
        return search(root, key);
    }

    private BSTNode<T, K> search(BSTNode<T, K> root, K key) {
        if (root == null) {
            return null;
        }
        else if (root.getKey().compareTo(key) == 0) {
            while (root != null) {
                if (root.getKey().toString().equals(key.toString())) {
                    break;
                }
                else {
                    counter++;
                    address = address+"L";
                    root = root.left;
                }
            }
            return root;
        }
        else if (root.getKey().compareTo(key) < 0) {
            counter++;
            address = address+"R";
            return search(root.right, key);
        }
        counter++;
        address = address+"L";
        return search(root.left, key);
    }

    boolean deleteBST(K key) {
        counter = 1;
        deleteError = false;
        //System.out.println(key.toString());
        root = delete(root, key);
        return !deleteError;
    }

    private BSTNode<T,K> delete(BSTNode<T,K> root, K key) {
        if (root == null) {
            deleteError = true;
            return null;
        }
        else if (root.getKey().compareTo(key) == 0) {
            //System.out.println("here f");
            while (root != null) {
                if (root.getKey().toString().equals(key.toString())) {
                    //System.out.println("here f");
                    break;
                }
                else {
                    //System.out.println("here f");
                    counter++;
                    root = root.left;
                }
            }
            if (root == null) {
                //System.out.println("here n");
                deleteError = true;
                return null;
            }
            else if (root.left == null) {
                //System.out.println("here l");
                counter++; // TODO check
                return root.right;
            }
            else if (root.right == null) {
                //System.out.println("here r");
                counter++; // TODO check
                return root.left;
            }
            //System.out.println("here i");
            counter++; // TODO check
            BSTNode<T, K> insucc = inorderSuccessor(root.right);
            //System.out.println(insucc.getKey().toString());
            root.setKey(insucc.getKey());
            root.setData(insucc.getData());
            counter++; // TODO check
            root.right = delete(root.right, insucc.getKey());
        }
        else if (root.getKey().compareTo(key) < 0) {
            if (root.left == null) {
                //System.out.println("here");
                deleteError = true;
                return null;
            }
            counter++;
            root.left = delete(root.left, key);
        }
        else {
            if (root.right == null) {
                //System.out.println("here2");
                deleteError = true;
                return null;
            }
            counter++;
            root.right = delete(root.right, key);
        }
        return root;
    }

    private BSTNode<T, K> inorderSuccessor(BSTNode<T, K> root) {
        BSTNode<T, K> successor = root;
        while (root.left != null) {
            counter++; // TODO check
            successor = root.left;
            root = root.left;
        }
        return successor;
    }
}
