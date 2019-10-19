package Trie;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class Trie<T> implements TrieInterface {

    private TrieNode<T> root;
    private int trieNodeSize = 95;
    private static boolean isPrintableLevel = true;
    private static boolean deleteError = false;

    public Trie() {
        this.root = new TrieNode<T>(null, trieNodeSize);
    }

    private boolean isEmpty(TrieNode<T> root) {
        for (int i = 0; i < trieNodeSize; i++) {
            if (root.childNodes[i] != null){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(String word) {
        deleteError = false;
        deleter(root, 0, word);
        return !deleteError;
    }

    private TrieNode<T> deleter(TrieNode<T> root, int wordIndex, String word) {
        if (root == null) {
            deleteError = true;
            return null;
        }

        if (wordIndex == word.length()) {

            if (root.getValue() != null) {
                root.setValue(null);
            }
            if (isEmpty(root)) {
                root = null;  // node is null when value = null and all child nodes are null
            }

            return root;
        }

        int trieIndex = word.charAt(wordIndex) - 32;
        root.childNodes[trieIndex] = deleter(root.childNodes[trieIndex], wordIndex+1, word);

        if (isEmpty(root) && root.getValue() == null) { // after deleting childNode at trieIndex, if all nodes are null and node value is null then node is null
            root = null;
        }

        return root;
    }


    @Override
    public TrieNode search(String word) {
        TrieNode<T> currentNode = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 32;
            // System.out.println(word.charAt(i) + " " + index);
            if (currentNode.childNodes[index] == null) {
                return null;
            }
            else {
                currentNode = currentNode.childNodes[index];
            }
        }

        if (currentNode.getValue() != null) {
            return currentNode;
        }
        else return null;
    }

    @Override
    public TrieNode startsWith(String prefix) {
        TrieNode<T> currentNode = root;

        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 32;
            // System.out.println(word.charAt(i) + " " + index);
            if (currentNode.childNodes[index] == null) {
                return null;
            }
            else {
                currentNode = currentNode.childNodes[index];
            }
        }

        return currentNode;
    }

    @Override
    public void printTrie(TrieNode trieNode) {
        if (trieNode == null) return;
        if (trieNode.getValue() != null) {
            System.out.println(trieNode.getValue());
        }

        for (int i = 0; i < trieNodeSize; i++) {
            printTrie(trieNode.childNodes[i]);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insert(String word, Object value) {

        TrieNode<T> currentNode = root;
        boolean inserted = false;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 32;
            // System.out.println(word.charAt(i) + " " + index);
            if (currentNode.childNodes[index] == null) {
                currentNode.childNodes[index] = new TrieNode<T>(null, trieNodeSize);
            }
            currentNode = currentNode.childNodes[index];
        }
        if (currentNode.getValue() == null) {
            currentNode.setValue((T) value);
            inserted = true;
        }
//        else {
//            System.out.println("Duplicate key");
//        }
        return inserted;
    }

    @Override
    public void printLevel(int level) {

        int count = 1;
        /*
          q stores all nodes in the level
         */
        Queue<TrieNode<T>> q = new LinkedList<TrieNode<T>>();

        /*
          a stores all characters in the level
         */
        ArrayList<Character> a = new ArrayList<>();

        q.add(root);
        int queueSize = 1;
        while (count < level) {
            for (int i = 0; i < queueSize; i++) {
                TrieNode<T> t = q.remove();
                for (int j = 0; j < trieNodeSize; j++) {
                    if (t.childNodes[j] != null) {
                        q.add(t.childNodes[j]);
                    }
                }
            }
            queueSize = q.size();
            count++;
        }

        if (queueSize == 0) {
            isPrintableLevel = false;
            return;
        }

        for (int i = 0; i < queueSize; i++) {
            TrieNode<T> t = q.remove();
            for (int j = 0; j < trieNodeSize; j++) {
                if (t.childNodes[j] != null) {
                    a.add((char) (j+32));
                }
            }
        }

        if (a.size() == 0) {
            isPrintableLevel = false;
            return;
        }
        a.sort(null); // checked : sorts in natural ordering by default if comparator is null
        System.out.print("Level " + level + ": ");

        for (int i=0; i<a.size()-1; i++) {
            if (!a.get(i).equals(' ')) {
                System.out.print(a.get(i) + ",");
            }
        }
        System.out.print(a.get(a.size()-1));
        System.out.println();

    }

    @Override
    public void print() {
        System.out.println("-------------");
        System.out.println("Printing Trie");
        int level = 1;
        isPrintableLevel = true;
        while (isPrintableLevel) {
            printLevel(level);
            level++;
        }
        System.out.println("Level " + (level-1) + ": ");
        System.out.println("-------------");
    }
//
//    private ArrayList<T> traverseLevel(int level) {
//
//        int count = 1;
//        /*
//          q stores all nodes in the level
//         */
//        Queue<TrieNode<T>> q = new LinkedList<TrieNode<T>>();
//
//        /*
//          a stores all characters in the level
//         */
//        ArrayList<T> a = new ArrayList<>();
//
//        q.add(root);
//        int queueSize = 1;
//        while (count < level) {
//            for (int i = 0; i < queueSize; i++) {
//                TrieNode<T> t = q.remove();
//                for (int j = 0; j < trieNodeSize; j++) {
//                    if (t.childNodes[j] != null) {
//                        q.add(t.childNodes[j]);
//                    }
//                }
//            }
//            queueSize = q.size();
//            count++;
//        }
//
//        if (queueSize == 0){
//            isPrintableLevel=false;
//            return a;
//        }
//
//        for (int i = 0; i < queueSize; i++) {
//            TrieNode<T> t = q.remove();
//            for (int j = 0; j < trieNodeSize; j++) {
//                if (t.getValue() != null) {
//                    a.add(t.getValue());
//                }
//            }
//        }
//
//        return a;
//    }
//
//    public ArrayList<T> traverse() {
//        ArrayList<T> a1 = new ArrayList<>();
//
//        int level = 1;
//        isPrintableLevel = true;
//        while (isPrintableLevel) {
//            for (T t : traverseLevel(level)) {
//                a1.add(t);
//            }
//            level++;
//        }
//        return a1;
//    }

}