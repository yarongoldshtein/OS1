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
 * Thread that run over the Cache
 * @author אליצורד
 */
public class CThread extends Thread {

    private int y;
    private Cache cache;
    boolean runFlag = true;
    boolean getYFlag = false;
    private ArrayList<Integer> ArrayOfReq = new ArrayList<>();
    private final ReentrantLock lock2 = new ReentrantLock(true);
    private final ReentrantLock lock = new ReentrantLock(true);
    static int CThreadNo = 0 ;

    /**
     * CThread constractor
     */
    public CThread() {
        this.cache = Server.cache;
        y = 0;
    }

    /**
     * search the first query at the ArrayList in the cache 
     */
    @Override
    public void run() {
        Thread.currentThread().setName("CThread"+(CThreadNo++));
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
            lock2.lock();
            y = cache.search(ArrayOfReq.get(0));
            ArrayOfReq.remove(0);
            lock2.unlock();
            getYFlag = true;
        }
    }

    /**
     * 
     * @return pointer to ArrayOfReq
     */
    public ArrayList<Integer> getArrayOfReq() {
        lock.lock();
        try {
            return ArrayOfReq;
        } finally {
            lock.unlock();
        }

    }

    /**
     *  wait to answer our query from the cache
     * @return when he found the answer he return it ( if he don't found he return -1)
     * @throws InterruptedException 
     */
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
