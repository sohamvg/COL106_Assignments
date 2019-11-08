public class Queue<V>{

    private MyArrayList<V> queue;
    private int front;

    public Queue() {
        this.front = 0;
        queue = new MyArrayList<>();
    }

    public int size() {
        return queue.size();
    }

    int getCurrentSize() {
        return queue.size()-front;
    }

    boolean isEmpty() {
        return front >= queue.size();
    }

    public void enqueue(V element) {
        queue.add(element);
    }

    public V dequeue() {
        if (isEmpty()) {
            System.out.println("queue is empty!!");
            return null;
        }
        V temp = queue.get(front);
        front = front + 1;
        return temp;

    }

}