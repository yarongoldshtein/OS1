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
public class CThread implements Runnable {

    private ArrayList<Integer> ArrayOfReq = new ArrayList<Integer>();
    private int y;
    private final ReentrantLock lock = new ReentrantLock(true);
    private Cache cache;

    public CThread(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        while (ArrayOfReq.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println("CThread can't sleep");
            }
        }
       y =cache.search(ArrayOfReq.get(0));
       ArrayOfReq.remove(0);
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
        return y;
    }
}
