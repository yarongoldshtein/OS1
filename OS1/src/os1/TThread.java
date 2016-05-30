/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author אליצור
 */
public class TThread extends Thread {

    private int y;
    private int x;
    private cacheNode cn;
    private boolean found = true;
    private ReadWriteLockByNumber rwl = new ReadWriteLockByNumber();
    private ReentrantLock lock = new ReentrantLock(true);
    static ArrayList<Integer> wasInTheDb = new ArrayList<>();
    private ReentrantLock lock2 = new ReentrantLock(true);

    private PrintWriter out;

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

    @Override
    public void run() {
        lock2.lock();
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
