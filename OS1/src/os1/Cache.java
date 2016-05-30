package os1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author אליצור
 */
public class Cache {

    private LockedHashMap<Integer, cacheNode> myCache;
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

    public void upDateCache() {
        if (candidates.size() >= sizeOfCandidates) {

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
            System.err.println(candidates.toString() + "  cand  " + M);
            System.err.println(myCache.toString() + "    " + M);
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
