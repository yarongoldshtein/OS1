/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class CThread extends Thread {

    private ArrayList<Integer> ArrayOfReq = new ArrayList<Integer>();
    private int y;
    private final ReentrantLock lock = new ReentrantLock(true);
    private Cache cache;
    private final ReentrantLock lock2 = new ReentrantLock(true);
    private boolean LockY;

    ;
    public CThread(Cache cache) {
        this.cache = cache;
        y = 0;
    }

    @Override
    public void run() {
        while (true) {
            lock2.lock();
            while (ArrayOfReq.isEmpty()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    System.out.println("CThread can't sleep");
                }
            }

            y = cache.search(ArrayOfReq.get(0));
            lock2.unlock();
            while (LockY) {

            }
            System.out.println("y in the Thread = " + y);
            ArrayOfReq.remove(0);

        }
    }

    public ArrayList<Integer> getArrayOfReq() {
        lock.lock();
        try {
            return ArrayOfReq;
        } finally {
            lock.unlock();
        }

    }

    public int getY() {
        lock2.lock();
        try {
            return y;
        } finally {
            LockY = false;
            lock2.unlock();
        }
    }
}
