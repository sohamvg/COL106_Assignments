package ProjectManagement;

import PriorityQueue.MaxHeap;

import java.util.Comparator;

public class JobArrivalComparator implements Comparator<MaxHeap.Node<Job>> {

    @Override
    public int compare(MaxHeap.Node<Job> jobNode, MaxHeap.Node<Job> t1) {
        return Integer.compare(jobNode.getElement().getPreciseArrivalTime(), t1.getElement().getPreciseArrivalTime());
    }
}
