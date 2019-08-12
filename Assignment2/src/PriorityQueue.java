
public class PriorityQueue<V> implements QueueInterface<V>{

    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
	
    //TODO Complete the Priority Queue implementation
    // You may create other member variables/ methods if required.
    public PriorityQueue(int capacity) {    
        this.capacity = capacity;
        this.currentSize = 0;
        this.front = 0;
        this.rear = capacity - 1;
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
            System.out.println("priority queue overflow");
        }
        else {
            if (currentSize == 0) {
                rear = (rear + 1)%capacity;
                queue[rear] = node;
                currentSize++;
            }
            else {
                boolean notReachedFront = true;
                int i = rear;
                queue[(i+1)%capacity] = node;
                while (notReachedFront) {

                    if (node.getPriority() < queue[i].getPriority()) {
                        NodeBase<V> temp = queue[i];
                        queue[i] = node;
                        queue[(i+1)%capacity] = temp;
                    } else {
                        break;
                    }
                    if (i == front) notReachedFront = false;
                    i = (i - 1)%capacity;
                    if (i<0) i+=capacity;
                }
                rear = (rear + 1)%capacity;
                currentSize++;
            }
        }
    }

    // In case of priority queue, the dequeue() should 
    // always remove the element with minimum priority value
    public NodeBase<V> dequeue() {
        if (isEmpty()) {
            System.out.println("priority queue is empty");
            return null;
        }
        NodeBase<V> temp = queue[front];
        front = (front + 1)%capacity;
        currentSize = currentSize - 1;
        return temp;

    }

    public void display () {
	if (this.isEmpty()) {
            System.out.println("Queue is empty");
	}
	for(int i=0; i<currentSize; i++) {
            queue[i].show();
	}
    }
}

