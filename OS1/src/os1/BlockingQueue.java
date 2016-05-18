package os1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yaron
 */
public class BlockingQueue {

    private List queue = new LinkedList();
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition cond = lock.newCondition();

    public void enqueue(Object item) throws InterruptedException {
        lock.lock();
        try {
            this.queue.add(item);
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Object dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (this.queue.isEmpty()) {
                cond.await();
            }
            return this.queue.remove(0);
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}
