// This class implements the Queue
public class Queue<V> implements QueueInterface<V>{

    //TODO Complete the Queue implementation
    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
	
    public Queue(int capacity) {
    	this.capacity = capacity;
    	this.front = 0;
    	this.rear = capacity - 1;
    	this.currentSize = 0;
    	//TODO check generic array
		queue = new Node[capacity];
    }

    public int size() {
		return currentSize;
    
    }

    public boolean isEmpty() {
		return currentSize == 0;
    
    }
	
    public boolean isFull() {
		return currentSize == capacity;
    
    }

    public void enqueue(Node<V> node) {
    	if (isFull()) {
			System.out.println("queue overflow");
		} else {
			rear = (rear + 1)%capacity;
    		queue[rear] = node;
        	currentSize++;	
    	}
    	
    }

    public NodeBase<V> dequeue() {
		if (isEmpty()) {
			System.out.println("queue is empty");
			return null;
		}
		NodeBase<V> temp = queue[front];
		front = (front + 1)%capacity;
		currentSize = currentSize - 1;
		return temp;
    
    }

}
