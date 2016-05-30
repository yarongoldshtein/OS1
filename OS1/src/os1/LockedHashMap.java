/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author אליצור
 */
public class LockedHashMap<Integer,cacheNode> {

    private HashMap<Integer, cacheNode> myHash;
    private ReentrantLock lock = new ReentrantLock(true);

    public LockedHashMap() {
        lock.lock();
        try {
            myHash = new HashMap<>();
        } finally {
            lock.unlock();
        }
    }

    public void put(Integer i, cacheNode cn) {
        lock.lock();
        try {
            myHash.put(i, cn);
        } finally {
            lock.unlock();
        }

    }

    public void putAll(Map < ? extends Integer,? extends cacheNode> map){
        lock.lock();
        try{
           myHash.putAll(map);
        }finally{
            lock.unlock();
        }
    }
    
    public cacheNode get(Object i) {
        lock.lock();
        try {
            cacheNode value = myHash.get(i);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public Collection<cacheNode> values() {
        lock.lock();
        try {
            Collection<cacheNode> values = myHash.values();
            return values;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return myHash.size();
        } finally {
            lock.unlock();
        }
    }

    public Set< Entry<Integer, cacheNode>> entrySet() {
        lock.lock();
        try {
            return myHash.entrySet();
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();
        try {
            myHash.clear();
        } finally {
            lock.unlock();
        }
    }

    public Boolean containsKey(Object o){
        lock.lock();
        try{
            return myHash.containsKey(o);
        }finally{
            lock.unlock();
        }
    }
    
    @Override
    public String toString(){
        lock.lock();
        try{
            return myHash.toString();
        }finally{
            lock.unlock();
        }
    }
}
