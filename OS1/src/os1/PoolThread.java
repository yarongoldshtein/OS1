/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author אליצור
 */
public class PoolThread extends Thread {

    private BlockingQueue taskQueue = null;
    private boolean isStopped = false;
    final Lock lock = new ReentrantLock();

    public PoolThread(BlockingQueue queue) {
        taskQueue = queue;
    }

    public void run() { ///////לשאול את מנחם סמט אם זה אותו ה-run
        while (!isStopped()) {
            try {
                Runnable runnable = null;
               taskQueue.dequeue(runnable);
                runnable.run();
            } catch (Exception e) {
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public void doStop() {
        try {
            lock.lock();
            isStopped = true;
            this.interrupt(); //break pool thread out of dequeue() call.
        } finally {
            lock.unlock();
        }

    }

    public boolean isStopped() { // בעיה עם ההחזרה ושחרור הנעילה
        try {
            lock.lock();
            return isStopped;
        } finally {
            lock.unlock();
        }
        
    }
}
