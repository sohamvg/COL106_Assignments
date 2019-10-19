package PriorityQueue;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements PriorityQueueInterface<T> {

    private int heapTime = 0;

    public static class Node<t extends Comparable<t>> implements Comparable<Node<t>> {
        t element;
        int time;

        public Node(t element, int time) {
            this.element = element;
            this.time = time;
        }

        public t getElement() {
            return element;
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

    /**
     * @param i index of element to be moved up
     *          O(log i)
     *          worst case = O(log n), n is no. of elements in heap
     */
    private void moveUp(int i) {
        while (i > 0) {
            int j = parent(i);
            if (maxHeap.get(i).compareTo(maxHeap.get(j)) <= 0) { // if both element have same priority then break
                break;
            }
            else {
                swap(i, j);
                i = j;
            }
        }
    }

    /**
     * Time complexity of inserting a new element is O(log n), n is number of nodes initially present in the heap <br>
     * Time complexity of inserting 'm' elements in initially empty heap is O(m*log m) <br>
     * @param element Insert and element to the Priority Queue
     */
    @Override
    public void insert(T element) {
        heapTime+=1;
        Node<T> heapElement = new Node<>(element, heapTime);
        maxHeap.add(heapElement); // could be at any of left or right of root

        moveUp(maxHeap.size()-1);
    }

    /**
     * Time complexity of extracting the max element is O(log n), n is no. of nodes present in the heap
     * @return max element
     */
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
            int big = l;

            if (right(i) < maxHeap.size()) {
                int r = right(i);
                if (maxHeap.get(l).compareTo(maxHeap.get(r)) < 0) {
                    big = r;
                }
            }

            if (maxHeap.get(big).compareTo(maxHeap.get(i)) <= 0) {
                break;
            }
            swap(i, big);
            i = big;
        }

        return t.element;
    }

    private void moveDown(ArrayList<Node<T>> maxHeap, int i) {
        while (left(i) < maxHeap.size()) {
            int l = left(i);
            int big = l;

            if (right(i) < maxHeap.size()) {
                int r = right(i);
                if (maxHeap.get(l).compareTo(maxHeap.get(r)) < 0) {
                    big = r;
                }
            }
            if (maxHeap.get(big).compareTo(maxHeap.get(i)) <= 0) {
                break;
            }
            Node<T> temp = maxHeap.get(i);
            maxHeap.set(i, maxHeap.get(big));
            maxHeap.set(big, temp);
            i = big;
        }
    }

    public void buildHeap(ArrayList<Node<T>> arr, int n)
    {
        if (n <= 0) return;
        int lastNonLeafNode = parent(n);

        for (int i = lastNonLeafNode; i >= 0; i--) {
            moveDown(arr, i);
        }
    }


    public Node<T> extractMax2() {
        if (maxHeap.size() <= 0) return null;

        if (maxHeap.size() == 1) {
            return maxHeap.remove(0);
        }

        Node<T> t = maxHeap.get(0);
        swap(0, maxHeap.size()-1);
        maxHeap.remove(maxHeap.size()-1);

        int i = 0;
        while (left(i) < maxHeap.size()) {
            int l = left(i);
            int big = l;

            if (right(i) < maxHeap.size()) {
                int r = right(i);
                if (maxHeap.get(l).compareTo(maxHeap.get(r)) < 0) {
                    big = r;
                }
            }

            if (maxHeap.get(big).compareTo(maxHeap.get(i)) <= 0) {
                break;
            }
            swap(i, big);
            i = big;
        }

        return t;
    }

/*
/////////////////// search function //////////////////////

    public T search(T element) {
        for (Node<T> t : maxHeap) {
            if (t != null && t.element.equals(element)) {
                return element;
            }
        }
        return null;
    }
*/

    /**
     * Time complexity is O(1) since ArrayList.size() runs in constant time
     *
     */
//    public int heapSize() {
//        return maxHeap.size();
//    }

//    public ArrayList<Node<T>> getMaxHeap() {
//        return maxHeap;
//    }

//    public T getMax() {
//        return maxHeap.get(0).element;
//    }

    public void setMaxHeap(ArrayList<Node<T>> maxHeap) {
        this.maxHeap = maxHeap;
    }


////////////////////// iterator /////////////////////////
//
//    public Iterator<Node<T>> heapIterator() {
//        return maxHeap.iterator();
//    }


}