package PriorityQueue;

import java.util.ArrayList;

/**
 * @param <T> element
 * Use only when element.compareTo is never 0
 */
public class MaxHeapNoNode<T extends Comparable<T>> implements PriorityQueueInterface<T> {

    private ArrayList<T> maxHeap;

    public MaxHeapNoNode() {
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
        T temp = maxHeap.get(i);
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
        maxHeap.add(element); // could be at any of left or right of root

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
            return maxHeap.remove(0);
        }

        T t = maxHeap.get(0);
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

    private void moveDown(ArrayList<T> maxHeap, int i) {
        while (left(i) < maxHeap.size() && i >= 0) {
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
            T temp = maxHeap.get(i);
            maxHeap.set(i, maxHeap.get(big));
            maxHeap.set(big, temp);
            i = big;
        }
    }

    public void buildHeap(ArrayList<T> arr, int n)
    {
        if (n <= 0) return;
        int lastNonLeafNode = parent(n);

        for (int i = lastNonLeafNode; i >= 0; i--) {
            moveDown(arr, i);
        }
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
     * @return heap size
     */
    public int heapSize() {
        return maxHeap.size();
    }

    public ArrayList<T> getMaxHeap() {
        return maxHeap;
    }

    public void setMaxHeap(ArrayList<T> maxHeap) {
        this.maxHeap = maxHeap;
    }
//    public T getMax() {
//        return maxHeap.get(0).element;
//    }


////////////////////// iterator /////////////////////////
//
//    public Iterator<Node<T>> heapIterator() {
//        return maxHeap.iterator();
//    }


}