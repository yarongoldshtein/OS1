package server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yaron
 */
public class ThreadPool {

    private boolean isStopped = false;
    private List<PoolThread> threads = new ArrayList<>();
    private BlockingQueue taskQueue = new BlockingQueue();
    private final ReentrantLock lock = new ReentrantLock(true);

    /**
     * ThreadPool constractor
     * @param numOfThreads can work togeter in the ThreadPool
     */
    public ThreadPool(int numOfThreads) {
        for (int i = 0; i < numOfThreads; i++) {
            threads.add(new PoolThread(taskQueue));
        }
        for (PoolThread thread : threads) {
            thread.start();
        }
    }

    /**
     * insert the task to taskQueue
     * @param task to insert
     * @throws InterruptedException if the ThreadPool Stopped
     */
    public void execute(Runnable task) throws InterruptedException {
        lock.lock();
        try {
            if (isStopped) {
                throw new IllegalStateException("ThreadPool is stopped");
            }
            this.taskQueue.enqueue(task);
        } finally {
            lock.unlock();
        }

    }

    /**
     * stop the ThreadPool work
     */
    public void stop() {
        lock.lock();
        try {
            this.isStopped = true;
            for (PoolThread thread : threads) {
                thread.doStop();
            }
        } finally {
            lock.unlock();
        }
    }
}
