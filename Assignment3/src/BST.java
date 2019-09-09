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
            if (root.right != null) {
                root.right = insert(root.right, data, key);
            }
            else {
                root.right = new BSTNode<>(data, key);
            }
        }
        else { // Node is inserted to left if first name is same
            counter++;
            if (root.left != null) {
                root.left = insert(root.left, data, key);
            }
            else {
                root.left = new BSTNode<>(data, key);
            }
        }
        return root;
    }

    BSTNode<T, K> searchBST(K key) {
        counter = 1; // root is touched
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

                    if (root.left != null) {
                        counter++;
                        //System.out.println("here eq" + counter) ;
                        address = address+"L";
                        root = root.left;
                    }
                    else {
                        return null;
                    }

                }
            }
            return root;
        }
        else if (root.getKey().compareTo(key) < 0) {
            if (root.right == null) {
                return null;
            }
            else {
                counter++;
                //System.out.println("key less" + counter);
                address = address+"R";
                return search(root.right, key);
            }

        }
        else if (root.left == null) {
            return null;
        }
        counter++;
        //System.out.println("key more" + counter);
        address = address+"L";
        return search(root.left, key);
    }

    boolean deleteBST(K key) {
        counter = 1; // touched root
        deleteError = false;
        //System.out.println(key.toString());
        root = delete(root, key);
        return !deleteError;
    }

    private BSTNode<T,K> delete(BSTNode<T,K> root, K key) {
        if (root == null) {
            deleteError = true;
    //        System.out.println("here 1");
            return null;
        }
        else if (root.getKey().compareTo(key) == 0) {
            while (root != null) {
                if (root.getKey().toString().equals(key.toString())) {
                    //System.out.println("here f");
                    break;
                }
                else {
            //        System.out.println("here f");
                    if (root.left != null) {
                        counter++; // touched root.left
                        root = root.left;
                    }
                    else {
                        deleteError = true;
                        return null;
                    }
                }
            }
            if (root == null) {
        //        System.out.println("here n");
                deleteError = true;
        //        System.out.println("here 2");
                return null;
            }
            else if (root.left == null) {

                if (root.right != null) { // touched root.right
                    counter++;
        //            System.out.println("here l" + counter);
                }

                return root.right;
            }
            else if (root.right == null) {

                counter++; // touched root.left
        //        System.out.println("here l" + counter);
                return root.left;
            }

            counter++; // touched root.right TODO check
        //    System.out.println("here i" + counter);
            Object[] insucc2 = inorderSuccessor2(root.right);
            BSTNode<T, K> p = (BSTNode<T, K>) insucc2[0];
            BSTNode<T, K> r = (BSTNode<T, K>) insucc2[1];
            root.setKey(r.getKey());
            root.setData(r.getData());
//            System.out.println("here too");
//            counter++; // TODO check
            if (p.left == null) { // when insucc is -R // p is already touched and nothing to touch in p.left
                root.right = delete(p, r.getKey());
            }
            else { // when is insucc is -RLLL.... // p.left is nothing but r which is already touched
                p.left = delete(p.left, r.getKey());
            }

        }
        else if (root.getKey().compareTo(key) >= 0) {
            if (root.left == null) {
         //       System.out.println("here");
                deleteError = true;
                return null;
            }
            counter++; // touched root.left
        //    System.out.println("key less" + counter);
            root.left = delete(root.left, key);
        }
        else {
            if (root.right == null) {
         //       System.out.println("here 3");
                deleteError = true;
                return null;
            }

            counter++; // touched root.right
        //    System.out.println("key more" + counter);
            root.right = delete(root.right, key);
        }
        return root;
    }

//    private BSTNode<T, K> inorderSuccessor(BSTNode<T, K> root) {
//        //BSTNode<T, K> prev = root;
//        while (root.left != null) {
//            counter++;
//            //successor = root.left;
//            //prev = root;
//            root = root.left;
//            //root = successor;
//        }
//        return root;
//    }

    private Object[] inorderSuccessor2(BSTNode<T, K> root) {
        Object[] res = new Object[2];
        /*
        res[0] = prev // initial = root
        res[1] = root // initial = root
         */
        BSTNode<T, K> prev = root;
        while (root.left != null) {
        //    System.out.println("here insuxx");
            counter++; // touched root.left
            prev = root;
            root = root.left;
        }
        res[0] = prev;
        res[1] = root;
        return res;
    }
}
