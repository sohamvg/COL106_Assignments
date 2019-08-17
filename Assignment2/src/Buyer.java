import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Buyer<V> extends BuyerBase<V> {
    public Buyer (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, int iteration) {
        //TODO Complete the Buyer Constructor method
        // ...
        this.catalog = catalog;
        this.empty = empty;
        this.full = full;
        this.lock = lock;
    }
    public void buy() throws InterruptedException {
        lock.lock();
        try {
            //TODO Complete the try block for consume method
            // ...
            while (catalog.isEmpty()) {
                empty.await();
            }
            NodeBase<V> n = catalog.dequeue();
            full.signalAll();
	    System.out.print("Consumed "); // DO NOT REMOVE (For Automated Testing)
            n.show(); // DO NOT REMOVE (For Automated Testing)
            // ...
	} catch (Exception e) {
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
    public void setIteration(int iteration) {
        super.setIteration(iteration);
    }

    @Override
    public void run() {
        super.run();
    }
}
