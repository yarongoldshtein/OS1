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
    boolean Yflag = false;
    boolean Rflag = true;
//    private final ReentrantLock lock2 = new ReentrantLock(true);
//    private final ReentrantLock lock3 = new ReentrantLock(true);

    ;
    public CThread(Cache cache) {
        this.cache = cache;
        y = 0;
    }

    @Override
    public void run() {
//        lock3.lock();
        while (true) {
//            lock2.lock();
            while (!Rflag) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            Rflag = false;

//                try {
            while (ArrayOfReq.isEmpty()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    System.out.println("CThread can't sleep");
                }
            }
            y = cache.search(ArrayOfReq.get(0));
//                } finally {
//                lock3.unlock();
//                }
            ArrayOfReq.remove(0);

            Yflag = true;
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
//        lock3.lock();
//        try {
        while (!Yflag) {
            Thread.sleep(50);

        }
        Yflag = false;
        try {
            return y;
        } finally {
            Rflag = true;
        }
//        } finally {
//            lock2.unlock();
//        }
    }
}
