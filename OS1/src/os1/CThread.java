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

    private ArrayList<Integer> ArrayOfReq = new ArrayList<>();
    private int y;
    private final ReentrantLock lock = new ReentrantLock(true);
    private Cache cache;
    boolean runFlag = true;
    boolean getYFlag = false;
    private final ReentrantLock lock2 = new ReentrantLock(true);

    ;
    public CThread() {
        this.cache = Server.cache;
        y = 0;
    }

    @Override
    public void run() {
        while (true) {
            lock2.lock();
            try {
                while (!runFlag) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                runFlag = false;
            } finally {
                lock2.unlock();
            }
            while (ArrayOfReq.isEmpty()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    System.out.println("CThread can't sleep");
                }
            }
            y = cache.search(ArrayOfReq.get(0));

            ArrayOfReq.remove(0);

            getYFlag = true;
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

    public int getY() throws InterruptedException {
        while (!getYFlag) {
            Thread.sleep(50);
        }
        getYFlag = false;
        try {
            return y;
        } finally {
            runFlag = true;
        }
    }
}
