/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author אליצור
 */
public class BlockingQueue {

    private List queue = new LinkedList();
    private int limit = 10;
    final Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    public void enqueue(Object item) throws InterruptedException {
        try {
            lock.lock();
            while (this.queue.size() == this.limit) {
                cond.await();
            }
            if (this.queue.size() == 0) {
                cond.signalAll();
            }
            this.queue.add(item);
        } finally {
            lock.unlock();
        }

    }

    public  void dequeue(Runnable runnable) throws InterruptedException {// בעיה עם ההחזרה ושחרור הנעילה
        try {
            lock.lock();
             while (this.queue.size() == 0) { //cheack if queue empty
            cond.await();
        }
        if (this.queue.size() == this.limit) {
            cond.signalAll();
        }
        runnable = (Runnable)this.queue.remove(0);       
        } finally {
            lock.unlock();
        }
         
    }

}
