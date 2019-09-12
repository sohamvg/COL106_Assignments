class BST<T, K extends Comparable<K>> {
    private BSTNode<T, K> root;
    private static int counter = 0;
    private static String address = "";
    private static boolean deleteError = false;
    private static boolean insertError = false;

    BST() {
        root = null;
    }

    int getCounter() {
        return counter;
    }

    String getAddress() {
        return address;
    }

    boolean insertBST(T data, K key)
    {
        insertError = false;
        counter = 1;
        root = insert(root, data, key);
        return !insertError;
    }

    private BSTNode<T, K> insert(BSTNode<T, K> root, T data, K key) {/////////////////////
        if (root == null) {
            root = new BSTNode<>(data, key);
            return root;
        }
        else if (root.getKey().compareTo(key) > 0) {
            counter++;
            if (root.left != null) {
                root.left = insert(root.left, data, key);
            }
            else {
                root.left = new BSTNode<>(data, key);
            }
        }
        else { // Node is inserted to right if first name is same
            if (root.getKey().toString().equals(key.toString())) { // same fullname
                insertError = true;
            }
            else {
                counter++;
                if (root.right != null) {
                    root.right = insert(root.right, data, key);
                } else {
                    root.right = new BSTNode<>(data, key);
                }
            }
        }
        return root;
    }

    BSTNode<T, K> searchBST(K key) {
        //System.out.println("here 0");
        counter = 1; // root is touched
        address = "";
        return search(root, key);
    }

    private BSTNode<T, K> search(BSTNode<T, K> root, K key) { ////////////////////

        if (root == null) {
            //System.out.println("here 1");
            return null;
        }

        else if (root.getKey().compareTo(key) > 0) {

            if (root.left != null) {
                counter++; // touched root.left
                //System.out.println("key less" + counter);
                address = address + "L";
            }
                return search(root.left, key);
        }
        else {
            if (root.getKey().toString().equals(key.toString())) {
                //System.out.println("here alpha");
                return root;
            }
            else {
                if (root.right != null) {
                    counter++; // touched root.right
                    //System.out.println("key more" + counter);
                    address = address + "R";
                }
                return search(root.right, key);
            }
        }

    }

    boolean deleteBST(K key) {
        counter = 1; // touched root
        deleteError = false;
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

            if (!root.getKey().toString().equals(key.toString())) {
                if (root.right == null) {
                    //       System.out.println("here");
                    deleteError = true;
                    return root; ////////////////
                }
                counter++; // touched root.left
                //    System.out.println("key less" + counter);
                root.right = delete(root.right, key);
            }
            else {
                if (root.left == null) {

                    if (root.right != null) { // touched root.right
                        counter++;
                        //            System.out.println("here l" + counter);
                    }

                    //return root.right;
                    root = root.right;
                } else if (root.right == null) {

                    counter++; // touched root.left
                    //        System.out.println("here l" + counter);
                    // return root.left;
                    root = root.left;
                }

                else {
                    counter++; // touched root.right
                    //    System.out.println("here i" + counter);
                    BSTNode<T, K>[] insucc2 = inorderSuccessor2(root.right);
                    BSTNode<T, K> p = insucc2[0];
                    BSTNode<T, K> r = insucc2[1];
                    root.setKey(r.getKey());
                    root.setData(r.getData());
//            System.out.println("here too");
//            counter++; //
                    if (p.left == null) { // when insucc is -R // p is already touched and nothing to touch in p.left
                        //root.right = delete(p, r.getKey());
                        if (root.right.right != null) {
                            counter++; // touched root.right.right
                        }
                        root.right = root.right.right;
                    } else { // when is insucc is -RLLL.... // p.left is nothing but r which is already touched
                        //p.left = delete(p.left, r.getKey());
                        if (r.right != null){
                            counter++; // touched r.right
                        }
                        p.left = r.right;
                    }
                }
            }
        }
        else if (root.getKey().compareTo(key) > 0) {
            if (root.left == null) {
         //       System.out.println("here");
                deleteError = true;
                return root;
            }
            counter++; // touched root.left
        //    System.out.println("key less" + counter);
            root.left = delete(root.left, key);
        }
        else {
            if (root.right == null) {
         //       System.out.println("here 3");
                deleteError = true;
                return root;
            }

            counter++; // touched root.right
        //    System.out.println("key more" + counter);
            root.right = delete(root.right, key);
        }
        return root;
    }

    @SuppressWarnings("unchecked")
    private BSTNode<T, K>[] inorderSuccessor2(BSTNode<T, K> root) {
        BSTNode<T, K>[] res = new BSTNode[2];
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
