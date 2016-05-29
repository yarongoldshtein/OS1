/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

/**
 *
 * @author yaron
 */
public class ReadWriteLockByNumber {

    static SyncHashMap<Integer, ReadWriteLock> HashLock = new SyncHashMap<>();

    /**
     * locking the reader of the value has sent
     *
     * @param x - the value of reader to lock
     * @throws InterruptedException
     */
    public void ReadLock(int x) throws InterruptedException {
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
        HashLock.get(x).ReadUnlock();
    }

    /**
     * locking the writer of the value has sent
     *
     * @param x - the value of writer to lock
     * @throws InterruptedException
     */
    public void WriteLock(int x) throws InterruptedException {
        HashLock.get(x).WriteLock();

    }

    /**
     * unlocking the writer of the value has sent
     *
     * @param x - the value of writer to unlock
     * @throws InterruptedException
     */
    public void writeUnlock(int x) throws InterruptedException {
        HashLock.get(x).writeUnlock();
    }

}
