package server;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Threads in the ThreadPool
 * @author yaron
 */
public class PoolThread extends Thread {

    private BlockingQueue taskQueue = null;
    private boolean isStopped = false;
    private final ReentrantLock lock = new ReentrantLock(true);

    /**
     * poolThread constractor
     * @param queue 
     */
    public PoolThread(BlockingQueue queue) {
        taskQueue = queue;
    }

    /**
     * insert new poolthread to taskQueue of ThreadPool
     */
    @Override
    public void run() {
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.dequeue();
                runnable.run();
            } catch (InterruptedException e) {
                System.out.println("PoolThread crashed");
            }
        }
    }

    
    public void doStop() {
        lock.lock();
        try {
            isStopped = true;
            this.interrupt(); //break pool thread out of dequeue() call.
        } finally {
            lock.unlock();
        }
    }

    /**
     * 
     * @return isStooped
     */
    public boolean isStopped() {
        lock.lock();
        try {
            return isStopped;
        } finally {
            lock.unlock();
        }
    }
}
