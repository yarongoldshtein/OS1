/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.concurrent.Semaphore;

/**
 * ReadWriteLock Allowing several readers to enter together but when writing
 * inside it alone
 *
 * @author yaron
 */
public class ReadWriteLock {

    private int readers = 0;
    private int writers = 0;
    private final Semaphore Rmutex = new Semaphore(1, true);
    private final Semaphore Wmutex = new Semaphore(1, true);
    private final Semaphore Mutex2 = new Semaphore(1, true);
    private final Semaphore Rdb = new Semaphore(1, true);
    private final Semaphore Wdb = new Semaphore(1, true);

    /**
     * Read locker
     * @throws InterruptedException 
     */
    public void ReadLock() throws InterruptedException {
        Mutex2.acquire();
        Rdb.acquire();
        Rmutex.acquire();
        readers += 1;
        if (readers == 1) {
            Wdb.acquire();
        }
        Rmutex.release();
        Rdb.release();
        Mutex2.release();

    }

    /**
     * Read unlocker
     * @throws InterruptedException 
     */
    public void ReadUnlock() throws InterruptedException {
        Rmutex.acquire();
        readers -= 1;
        if (readers == 0) {
            Wdb.release();
        }
        Rmutex.release();
    }

    /**
     * Write locker
     * @throws InterruptedException 
     */
    public void WriteLock() throws InterruptedException {
        Wmutex.acquire();
        writers += 1;
        if (writers == 1) {
            Rdb.acquire();
        }
        Wmutex.release();
        Wdb.acquire();

    }

    /**
     * Write unlocker
     * @throws InterruptedException 
     */
    public void writeUnlock() throws InterruptedException {
        Wdb.release();
        Wmutex.acquire();
        writers -= 1;
        if (writers == 0) {
            Rdb.release();
        }
        Wmutex.release();
    }
}
