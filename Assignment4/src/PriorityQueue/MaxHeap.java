package PriorityQueue;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements PriorityQueueInterface<T> {

    private int heapTime = 0;

    static class Node<t extends Comparable<t>> implements Comparable<Node<t>> {
        t element;
        int time;

        Node(t element, int time) {
            this.element = element;
            this.time = time;
        }

        @Override
        public int compareTo(Node<t> tNode) {
            int tCompare = this.element.compareTo(tNode.element);
            if (tCompare == 0) {
                return Integer.compare(tNode.time, this.time);
            }
            return tCompare;
        }
    }

    private ArrayList<Node<T>> maxHeap;

    public MaxHeap() {
        this.maxHeap = new ArrayList<>();
    }

    private int parent(int t) {
        return (t-1) / 2;
    }

    private int left(int t) {
        return (2 * t) + 1;
    }

    private int right(int t) {
        return (2 * t) + 2;
    }

    private void swap(int i, int j) {
        Node<T> temp = maxHeap.get(i);
        maxHeap.set(i, maxHeap.get(j));
        maxHeap.set(j, temp);
    }

    private void moveUp(int i) {
        while (i > 0) {
            int j = parent(i);
            if (maxHeap.get(i).compareTo(maxHeap.get(j)) <= 0) { // is new element is equal then it is down the heap
                break;
            }
            else {
                swap(i, j);
                i = j;
            }
        }
    }


    @Override
    public void insert(T element) {
        heapTime+=1;
        Node<T> heapElement = new Node<>(element, heapTime);
        maxHeap.add(heapElement);

        moveUp(maxHeap.size()-1);
    }

    @Override
    public T extractMax() {
        if (maxHeap.size() <= 0) return null;

        if (maxHeap.size() == 1) {
            Node<T> t = maxHeap.remove(0);
            return t.element;
        }

        Node<T> t = maxHeap.get(0);
        swap(0, maxHeap.size()-1);
        maxHeap.remove(maxHeap.size()-1);

        int i = 0;
        while (left(i) < maxHeap.size()) {
            int l = left(i);
            int bg = l;

            if (right(i) < maxHeap.size()) {
                int r = right(i);
                if (maxHeap.get(l).compareTo(maxHeap.get(r)) < 0) {
                    bg = r;
                }
            }

            if (maxHeap.get(bg).compareTo(maxHeap.get(i)) <= 0) {
                break;
            }
            swap(i, bg);
            i = bg;
        }

        return t.element;
    }

/*
    public T search(T element) {
        for (Node<T> t : maxHeap) {
            if (t != null && t.element.equals(element)) {
                return element;
            }
        }
        return null;
    }
*/

    public int heapSize() {
        return maxHeap.size();
    }

}