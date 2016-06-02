/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author yaron
 */
public class ReadWriteLockByNumber {

    static LockedHashMap<Integer, ReadWriteLock> HashLock = new LockedHashMap<>();

    /**
     * locking the reader of the value has sent
     *
     * @param x - the value of reader to lock
     * @throws InterruptedException
     */
    public void ReadLock(int x) throws InterruptedException {
        x = fixX(x);
        if (HashLock.containsKey(x)) {
            HashLock.get(x).ReadLock();
        } else {
            HashLock.put(x, new ReadWriteLock());
            HashLock.get(x).ReadLock();

        }
    }

    /**
     * unlocking the reader of the value has sent
     *
     * @param x - the value of reader to unlock
     * @throws InterruptedException
     */
    public void ReadUnlock(int x) throws InterruptedException {
        x = fixX(x);
        HashLock.get(x).ReadUnlock();
    }

    /**
     * locking the writer of the value has sent
     *
     * @param x - the value of writer to lock
     * @throws InterruptedException
     */
    public void WriteLock(int x) throws InterruptedException {
        x = fixX(x);
        if (HashLock.get(x) != null) {
            HashLock.get(x).WriteLock();
        }
    }

    /**
     * unlocking the writer of the value has sent
     *
     * @param x - the value of writer to unlock
     * @throws InterruptedException
     */
    public void writeUnlock(int x) throws InterruptedException {
        x = fixX(x);
        if (HashLock.get(x) != null) {
            HashLock.get(x).writeUnlock();
        }
    }

    /**
     * get the query and return the file name
     * @param x the query
     * @return X as the file name
     */
    private int fixX(int x) {
        if (x >= 0) {
            x = x / Server.sizeOfDb;
        } else {
            x = x / Server.sizeOfDb - 1;
        }
        return x;
    }
}
