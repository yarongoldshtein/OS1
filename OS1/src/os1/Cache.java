package os1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author אליצור
 */
public class Cache {

    static LockedHashMap<Integer, cacheNode> myCache;
    private LockedHashMap<Integer, cacheNode> candidates;
    private int M;
    private final int C;
    private final int sizeOfCandidates;
    private final ReentrantLock lock = new ReentrantLock(true);

    public Cache(int m, int c) {
        M = m;
        C = c;
        sizeOfCandidates = c;
        myCache = new LockedHashMap<>();
        candidates = new LockedHashMap<>();
    }

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

    public void upDateCache() throws FileNotFoundException, IOException {
        if (candidates.size() >= sizeOfCandidates) {

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
//        myCache.putAll((Map<? extends Integer, ? extends cacheNode>) candidates);
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
        }
    }

    public int search(int x) {

        if (myCache.containsKey(x)) {
            myCache.get(x).setZ(myCache.get(x).getZ() + 1);
            return myCache.get(x).getY();
        }
        return -1;
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
