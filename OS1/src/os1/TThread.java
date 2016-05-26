/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Queue;

/**
 *
 * @author אליצור
 */
public class TThread extends Thread {

    private int y;
    private ReadWriteLock rwl = new ReadWriteLock();
    private HashMap<Integer, cacheNode> waitersToWriteInDb = new HashMap<>();
    private ReentrantLock lock = new ReentrantLock(true);
    private ArrayList<Integer> wasInTheDb = new ArrayList<>();
    private ReentrantLock lock2 = new ReentrantLock(true);

    public TThread(int x) {
        lock.lock();
        try {
            Server.ct.getArrayOfReq().add(x);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        final int x = Server.ct.getArrayOfReq().get(0);
        boolean updateHash = false;
        boolean foundInTheHash = false;
        boolean InTheHash = false;

        try {
            y = Server.ct.getY();
        } catch (InterruptedException ex) {
            Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (y == -1) {
            try {
                rwl.ReadLock();
                ReadDataBase rdb = new ReadDataBase(x);
                new Thread(rdb).start();
                y = rdb.getY();
                if (y == -1) {
                    lock2.lock();
                    while (InTheHash) {
                        Thread.sleep(50);
                    }
                    InTheHash = true;
                    lock2.unlock();
                    cacheNode tempNode = new cacheNode(waitersToWriteInDb.get(x));
                    if (tempNode != null) {
                        y = tempNode.getY();
                        updateHash = true;
                        foundInTheHash = true;
                    }
                }
                rwl.ReadUnlock();
            } catch (InterruptedException ex) {
                Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (updateHash) {//עדכון  זדים בהאשמפ
            try {
                rwl.WriteLock();
                waitersToWriteInDb.get(x).setZ(waitersToWriteInDb.get(x).getZ() + 1);
                updateHash = false;
                rwl.writeUnlock();
            } catch (InterruptedException ex) {
                Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                InTheHash = false;
            }
        }

        if (y == -1) {//dont found in the Database דחיפת דברים לתוך האשמפ
            try {

                y = ((Server.random + x) % Server.L) + 1;
                cacheNode cn = new cacheNode(x, y, 1);
                waitersToWriteInDb.put(x, cn);
                if (waitersToWriteInDb.size() == 50) {
                    Iterator<Map.Entry<Integer, cacheNode>> it = waitersToWriteInDb.entrySet().iterator();
                    while (it.hasNext()) {
                        rwl.WriteLock();
                        cacheNode enter = new cacheNode(it.next().getValue());
                        WriteDataBase wdb = new WriteDataBase(enter.getX(), enter.getY(), enter.getZ(), Server.sizeOfDb);
                        new Thread(wdb).start();
                        rwl.writeUnlock();
                    }
                    waitersToWriteInDb.clear();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                InTheHash = false;
            }

        } else if (!foundInTheHash) { // there is answer in the randomacsess
            wasInTheDb.add(x);
            if (wasInTheDb.size() == 50) {
                for (int i = 0; i < wasInTheDb.size(); i++) {
                    try {
                        rwl.WriteLock();
                        UpdateDataBase up = new UpdateDataBase(x);
                        new Thread(up).start();
                        rwl.writeUnlock();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                wasInTheDb.clear();
            }
        }
    }
}
