// Use this driver for the testing the correctness of your priority queue implementation
// You can change the add, remove sequence to test for various scenarios.
public class PriorityQueueTestDriver {
    public static void main(String[] args) {
		PriorityQueue<String> pq = new PriorityQueue<>(8);
		/*
		pq.add(4, "A");
		pq.add(10, "B");
		pq.add(3, "C");
		pq.add(5, "E");
		pq.add(2, "F");
		*/

		pq.enqueue(new Node<>(4,"A"));
		pq.enqueue(new Node<>(10,"b"));
		pq.enqueue(new Node<>(3,"c"));
		pq.enqueue(new Node<>(5,"e"));
		pq.enqueue(new Node<>(2,"f"));
		pq.enqueue(new Node<>(2,"f"));
		pq.enqueue(new Node<>(2,"f"));
		pq.enqueue(new Node<>(12,"f"));
	
	//pq.display();
	int size = pq.size();
	for (int i=0; i<size; i++) {
            Node<String> n = (Node<String>) pq.dequeue();
            n.show();
	}
    }
}
