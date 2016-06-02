/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Thread that Search at the Cache&Database
 *
 * @author אליצור
 */
public class TThread extends Thread {

    private int y;
    private int x;
    private cacheNode cn;
    private PrintWriter out;
    static int TThreadNo = 0;
    private boolean found = true;
    private ReentrantLock lock = new ReentrantLock(true);
    private ReentrantLock lock2 = new ReentrantLock(true);
    static ArrayList<Integer> wasInTheDb = new ArrayList<>();
    private ReadWriteLockByNumber rwl = new ReadWriteLockByNumber();

    /**
     * TThread constractor
     *
     * @param out Socket out for print the answer to the query
     * @param x the query
     */
    public TThread(PrintWriter out, int x) {
        lock.lock();
        try {
            Server.ct.getArrayOfReq().add(x);
            this.out = out;
            cn = new cacheNode();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Looking back caching if it finds an answer , if I did not go looking for
     * a database if it finds an answer back , if I did not write to a database
     * . Anyway do an update of the z instead found the answer . If the z
     * reaches m then it updates the cache
     */
    @Override
    public void run() {
        lock2.lock();
        Thread.currentThread().setName("TThreadNo" + (TThreadNo++));
        while (Server.ct.getArrayOfReq().get(0) == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        x = Server.ct.getArrayOfReq().get(0);
        cn.setX(x);
        lock2.unlock();

        try {
            y = Server.ct.getY();
        } catch (InterruptedException ex) {
        }

        if (y == -1) {
            try {
                rwl.ReadLock(x);
                ReadDataBase rdb = new ReadDataBase(x);
                Thread read = new Thread(rdb);
                Server.ReadersThreadPool.execute(read);
                cn = rdb.getNode();
                y = cn.getY();
                rwl.ReadUnlock(x);
            } catch (InterruptedException ex) {
                Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            out.println("" + y);
            out.flush();
        }

        if (y == -1) {
            y = (((Server.random + x) % Server.L) + 1);
            cn.setY(y);
            cn.setZ(1);
            found = false;
            out.println("" + y);
            out.flush();

        } else {
            out.println("" + y);
            out.flush();
        }
        try {
            rwl.WriteLock(x);
            WriteDataBase wdb = new WriteDataBase(x, cn, found);
            new Thread(wdb).start();
            rwl.writeUnlock(x);

        } catch (InterruptedException ex) {
            Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        found = true;
    }

}
