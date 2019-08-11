
public class PriorityQueue<V> implements QueueInterface<V>{

    //private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
    private Object[] queue;
	
    //TODO Complete the Priority Queue implementation
    // You may create other member variables/ methods if required.
    public PriorityQueue(int capacity) {    
        this.capacity = capacity;
        this.currentSize = 0;
        this.front = 0;
        this.rear = capacity - 1;
        //queue = (NodeBase<V>[]) new Object[capacity];
        queue = new Object[capacity];
    }

    private NodeBase<V> getNode(int i) {
        @SuppressWarnings(value = "unchecked")
        NodeBase<V> e = (NodeBase<V>) queue[i];
        return e;
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
        }
        else {
            if (currentSize == 0) {
                rear = (rear + 1)%capacity;
                //queue[rear] = node;
                queue[rear] = node;
            }
            else {
                int i = rear;
                while (true) {
                    if (node.getPriority() < getNode(i).getPriority()) {
                        queue[(i + 1)%capacity] = queue[i];
                    } else {
                        break;
                    }
                    i = (i - 1)%capacity;
                }
                queue[(i + 1)%capacity] = node;
                rear = (rear + 1)%capacity;
                currentSize++;
            }
        }
    }

    // In case of priority queue, the dequeue() should 
    // always remove the element with minimum priority value
    public NodeBase<V> dequeue() {
        if (isEmpty()) {
            System.out.println("queue is empty");
            return null;
        }   
        //NodeBase<V> temp = queue[front];
        NodeBase<V> temp = getNode(front);
        front = (front + 1)%capacity;
        currentSize = currentSize - 1;
        return temp;

    }

    public void display () {
	if (this.isEmpty()) {
            System.out.println("Queue is empty");
	}
	for(int i=0; i<currentSize; i++) {
            //queue[i+1].show();
        getNode(i+1).show();
	}
    }
}

