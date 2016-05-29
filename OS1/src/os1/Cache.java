
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

    private SyncHashMap<Integer, cacheNode> myCache;
    private SyncHashMap<Integer, cacheNode> candidates;
    private int M;
    private final int C;
    private final int sizeOfCandidates;
    private final ReentrantLock lock = new ReentrantLock(true);

    public Cache(int m, int c) {
        M = m;
        C = c;
        sizeOfCandidates = c;
        myCache = new SyncHashMap<>();
        candidates = new SyncHashMap<>();
    }

    public void insert(cacheNode cn) {

        lock.lock();
        try {
            cacheNode node = candidates.get(cn.getX());
            if (node != null) {
                node.setZ(node.getZ() + 1);
            } else {
                candidates.put(cn.getX(), cn);
                if (candidates.size() == sizeOfCandidates) {
                    upDateCache();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void upDateCache() {
        myCache.putAll((Map<? extends Integer, ? extends cacheNode>) candidates);
        cacheNode[] arrOfCn = new cacheNode[myCache.size()];
        int i = 0;
        Iterator<Map.Entry<Integer, cacheNode>> it = myCache.entrySet().iterator();
        while (it.hasNext()) {
            arrOfCn[i++] = it.next().getValue();
        }
        Arrays.sort(arrOfCn);
        myCache.clear();
        for (int j = arrOfCn.length-C; j < arrOfCn.length; j++) {
            myCache.put(arrOfCn[j].getX(), arrOfCn[j]);
        }
        candidates.clear();
        System.out.println(myCache.toString());
    }
    
    public int search(int x){
        
        if(myCache.containsKey(x)){
            myCache.get(x).setZ(myCache.get(x).getZ()+1);
            return myCache.get(x).getY();
        }
        return -1;
    }
    
}
