package server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BlockingQueue for ThreadPool
 * @author yaron
 */
public class BlockingQueue {

    private List queue = new LinkedList();
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition cond = lock.newCondition();

    /**
     * insert to the queue
     * @param item
     * @throws InterruptedException 
     */
    public void enqueue(Object item) throws InterruptedException {
        lock.lock();
        try {
            this.queue.add(item);
            cond.signalAll();
        } finally {         
            lock.unlock();
        }

    }

    /**
     * Removal of the object at the top of the queue
     * @return top of the queue
     * @throws InterruptedException 
     */
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

    /**
     * checking if the queue is empty
     * @return true- empty / false - there is Object in the queue
     */
    public boolean isEmpty() {
        lock.lock();
        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}
