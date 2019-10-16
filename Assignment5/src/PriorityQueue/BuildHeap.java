package PriorityQueue;

import java.util.ArrayList;

public class BuildHeap<T extends Comparable<T>> {

    private int parent(int t) {
        return (t-1) / 2;
    }

    private int left(int t) {
        return (2 * t) + 1;
    }

    private int right(int t) {
        return (2 * t) + 2;
    }

    private void swap(ArrayList<T> maxHeap, int i, int j) {
        T temp = maxHeap.get(i);
        maxHeap.set(i, maxHeap.get(j));
        maxHeap.set(j, temp);
    }

    private void moveDown(ArrayList<T> maxHeap, int i) {
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

    public T extractMax(ArrayList<T> maxHeap) {
        if (maxHeap.size() <= 0) return null;

        if (maxHeap.size() == 1) {
            return maxHeap.remove(0);
        }

        T t = maxHeap.get(0);
        swap(maxHeap, 0, maxHeap.size()-1);
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
            swap(maxHeap, i, big);
            i = big;
        }

        return t;
    }

}
