/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author אליצור
 */
public class ThreadPool {

    private BlockingQueue taskQueue = null;
    private List<PoolThread> threads = new ArrayList<PoolThread>();
    private boolean isStopped = false;
    private final Lock lock = new ReentrantLock();

    public ThreadPool(int numOfThreads, int maxNumOfTasks) {
        taskQueue = new BlockingQueue(maxNumOfTasks);

        for (int i = 0; i < numOfThreads; i++) {
            threads.add(new PoolThread(taskQueue));
        }
        for (PoolThread thread : threads) {
            thread.start();
        }
    }

    public  void execute(Runnable task) throws Exception {
        try {
            lock.lock();
            if (this.isStopped) {
            throw new IllegalStateException("ThreadPool is stopped");
        }

        this.taskQueue.enqueue(task);
        } finally {
            lock.unlock();
        }
        
    }

    public void stop() {
        try {
            lock.lock();
            this.isStopped = true;

            for (PoolThread thread : threads) {
                thread.doStop();
            }
        } finally {
            lock.unlock();
        }
    }

}
