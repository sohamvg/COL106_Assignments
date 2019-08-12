import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Seller<V> extends SellerBase<V> {
	
    public Seller (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, Queue<V> inventory) {
        //TODO Complete the constructor method by initializing the attibutes
        // ...
        this.inventory = inventory;
        this.catalog = catalog;
        this.lock = lock;
        this.empty = empty;
        this.full = full;
    }
    
    public void sell() throws InterruptedException {
	    lock.lock();
        try {
            //TODO Complete the try block for produce method
            // ...
            while (catalog.isFull()) {
                full.await();
            }

            synchronized (inventory) { //TODO check sync
                if (!inventory.isEmpty()) {
                    Node<V> item = (Node<V>) inventory.dequeue();
                    catalog.enqueue(item);
                }
            }
            empty.signal();
	} catch(Exception e) {
            e.printStackTrace();
	} finally {
            //TODO Complete this block
            lock.unlock();
	}
    }

    @Override
    public void setSleepTime(int sleepTime) {
        super.setSleepTime(sleepTime);
    }

    @Override
    public void run() {
        super.run();
    }
}
