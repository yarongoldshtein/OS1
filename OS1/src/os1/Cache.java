/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    private HashMap<Integer, cacheNode> myCache;
    private HashMap<Integer, cacheNode> candidates;
    private int M;
    private final int C;
    private final int sizeOfCandidates;
    private final ReentrantLock lock = new ReentrantLock(true);

    public Cache(int m, int c) {
        M = m;
        C = c;
        sizeOfCandidates = c;
        myCache = new HashMap<>();
        candidates = new HashMap<>();
    }

    public void insert(cacheNode cn) {

        lock.lock();
        try {
            cacheNode node = candidates.get(cn.getX());
            if (node != null) {
                cn.setZ(node.getZ() + 1);
            } else {
                candidates.put(cn.getX(), cn);
                cn.setZ(cn.getZ() + 1);
                if (candidates.size() == sizeOfCandidates) {
                    upDateCache();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void upDateCache() {
        myCache.putAll(candidates);
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
}
