package Trie;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {

    private T value;
    TrieNode<T>[] childNodes;

    TrieNode(T value, int size) {
        this.value = value;
        this.childNodes = new TrieNode[size];
        for (int i = 0; i < size; i++) {
            this.childNodes[i] = null;
        }
    }

    void setValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return this.value;
    }


}