package PriorityQueue;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements PriorityQueueInterface<T> {

    private ArrayList<T> maxHeap;
    static int timeOfInsertion;

    MaxHeap() {
        this.maxHeap = new ArrayList<>();
        MaxHeap.timeOfInsertion = 0;
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

//    private void moveDown(int i) {
//
//        while (left(i) < maxHeap.size()) {
//            int l = left(i);
//            int bg = l;
//
//            if (right(i) < maxHeap.size()) {
//                int r = right(i);
//                if (maxHeap.get(l).compareTo(maxHeap.get(r)) < 0) {
//                    bg = r;
//                }
//            }
//
//            if (maxHeap.get(bg).compareTo(maxHeap.get(i)) <= 0) {
//                break;
//            }
//            swap(i, bg);
//            i = bg;
//        }
//    }


//    private void maxHeapify(int i)
//    {
//        if (i >= maxHeap.size() / 2 && i <= maxHeap.size()) return;
//
//        if (maxHeap.get(i).compareTo(maxHeap.get(left(i))) < 0 || maxHeap.get(i).compareTo(maxHeap.get(right(i))) < 0) {
//            if (maxHeap.get(left(i)).compareTo(maxHeap.get(right(i))) > 0) {
//                swap(i, left(i));
//                maxHeapify(left(i));
//            }
//            else {
//                swap(i, right(i));
//                maxHeapify(right(i));
//            }
//        }
//    }

    @Override
    public void insert(T element) {
        maxHeap.add(element);
        MaxHeap.timeOfInsertion +=1;


        moveUp(maxHeap.size()-1);
        // if (maxHeap.size() >= 2) System.out.println(maxHeap.get(1).toString());

    }

    @Override
    public T extractMax() {
        if (maxHeap.size() <= 0) return null;

        T t = maxHeap.get(0);
        swap(0, maxHeap.size()-1);
        maxHeap.remove(maxHeap.size()-1);
        // moveDown(0);
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

        return t;
    }

}