package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author אליצור
 */
public class Cache {

    private int M;
    private final int C;
    private final int sizeOfCandidates;
    static LockedHashMap<Integer, cacheNode> myCache;
    private LockedHashMap<Integer, cacheNode> candidates;
    private final ReentrantLock lock = new ReentrantLock(true);
    static ReadWriteLock rwl = new ReadWriteLock();
    static ArrayList<Integer> InCache;

    /**
     * Cache constractor
     *
     * @param m
     * @param c
     */
    public Cache(int m, int c) {
        M = m;
        C = c;
        sizeOfCandidates = c;
        myCache = new LockedHashMap<>();
        candidates = new LockedHashMap<>();
        InCache = new ArrayList<>();
    }

    /**
     * insert CacheNode to candidates (to cache) , they come here if the Z > M
     *
     * @param cn - CacheNode is about to to candidates
     */
    public void insert(cacheNode cn) {

        lock.lock();
        try {
            cacheNode node = candidates.get(cn.getX());
            if (node != null) {
                node.setZ(node.getZ() + 1);
            } else {
                candidates.put(cn.getX(), cn);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks should update cache if need updating Starts updating the database
     * of contemporary data cache And then copies the same sorts cache
     * candidates and choose the biggest C and leave it deletes the cache and
     * updating the M size of the smallest element in the cache plus 1
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void upDateCache() throws FileNotFoundException, IOException, InterruptedException {
        if (candidates.size() >= sizeOfCandidates) {
            rwl.WriteLock();
            try {
                for (int i = 0; i < InCache.size(); i++) {
                    myCache.get(InCache.get(i)).setZ(myCache.get(InCache.get(i)).getZ() + 1);
                }
                InCache.clear();
                Iterator<Map.Entry<Integer, cacheNode>> itMyCache = myCache.entrySet().iterator();
                String nameOfFile;
                while (itMyCache.hasNext()) {
                    File dir = new File("DataBase");
                    cacheNode tempCn = itMyCache.next().getValue();
                    if (tempCn.getX() >= 0) {
                        nameOfFile = dir + "\\DataBaseNum" + (tempCn.getX() / Server.sizeOfDb) + ".txt";
                    } else {
                        nameOfFile = dir + "\\DataBaseNum" + ((tempCn.getX() / Server.sizeOfDb) - 1) + ".txt";
                        tempCn.setX(tempCn.getX() * (-1));
                    }
                    RandomAccessFile raf = new RandomAccessFile(nameOfFile, "rw");
                    raf.seek((tempCn.getX() % Server.sizeOfDb) * 8 + 4);
                    raf.writeInt(tempCn.getZ());
                }

                Iterator<Map.Entry<Integer, cacheNode>> candidatesIt = candidates.entrySet().iterator();
                while (candidatesIt.hasNext()) {
                    cacheNode tempCn = candidatesIt.next().getValue();
                    myCache.put(tempCn.getX(), tempCn);
                }
                cacheNode[] arrOfCn = new cacheNode[myCache.size()];
                int i = 0;
                Iterator<Map.Entry<Integer, cacheNode>> it = myCache.entrySet().iterator();
                while (it.hasNext()) {
                    arrOfCn[i++] = it.next().getValue();
                }
                Arrays.sort(arrOfCn);
                myCache.clear();
                for (int j = arrOfCn.length - C; j < arrOfCn.length; j++) {
                    myCache.put(arrOfCn[j].getX(), arrOfCn[j]);
                }
                M = arrOfCn[arrOfCn.length - C].getZ() + 1;
                candidates.clear();
            } finally {
                rwl.writeUnlock();
            }
        }
    }

    /**
     * Looking for query at the cache if he finds updating its z
     *
     * @param x our query
     * @return the answer ( If he had not found it returns -1)
     */
    public int search(int x) throws InterruptedException {
        rwl.ReadLock();
        try {
            if (myCache.containsKey(x)) {
                InCache.add(myCache.get(x).getX());
                return myCache.get(x).getY();
            }
            return -1;
        } finally {
            rwl.ReadUnlock();
        }
    }

    /**
     * return M - the minimum size of z neaded to enter the cache
     *
     * @return the minimum size of z neaded to enter the cache
     */
    public int getM() {
        return M;
    }

}
