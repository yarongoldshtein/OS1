/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * HashMap synchronized so there are'nt two Threads they can use the same object
 * together
 *
 * @author אליצור
 */
public class LockedHashMap<Integer, cacheNode> {

    private HashMap<Integer, cacheNode> myHash;
    static ReentrantLock lock = new ReentrantLock(true);

    /**
     * LockedHashmap constractor
     */
    public LockedHashMap() {
        lock.lock();
        try {
            myHash = new HashMap<>();
        } finally {
            lock.unlock();
        }
    }

    /**
     * put new data to the LockedHashmap
     *
     * @param i key
     * @param cn data
     */
    public void put(Integer i, cacheNode cn) {
        lock.lock();
        try {
            myHash.put(i, cn);
        } finally {
            lock.unlock();
        }

    }

    /**
     * Copies all of the mappings from the specified map to this map (optional
     * operation). The effect of this call is equivalent to that of calling
     * put(k, v) on this map once for each mapping from key k to value v in the
     * specified map. The behavior of this operation is undefined if the
     * specified map is modified while the operation is in progress.
     *
     * @param map - mappings to be stored in this map
     */
    public void putAll(Map< ? extends Integer, ? extends cacheNode> map) {
        lock.lock();
        try {
            myHash.putAll(map);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. More formally, if this map contains
     * a mapping from a key k to a value v such that (key==null ? k==null :
     * key.equals(k)), then this method returns v; otherwise it returns null.
     * (There can be at most one such mapping.) If this map permits null values,
     * then a return value of null does not necessarily indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to null. The containsKey operation may be used to
     * distinguish these two cases.
     *
     * @param i key - the key whose associated value is to be returned Returns:
     * the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key
     * @return
     */
    public cacheNode get(Object i) {
        lock.lock();
        try {
            cacheNode value = myHash.get(i);
            return value;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a Collection view of the values contained in this map. The
     * collection is backed by the map, so changes to the map are reflected in
     * the collection, and vice-versa. If the map is modified while an iteration
     * over the collection is in progress (except through the iterator's own
     * remove operation), the results of the iteration are undefined. The
     * collection supports element removal, which removes the corresponding
     * mapping from the map, via the Iterator.remove, Collection.remove,
     * removeAll, retainAll and clear operations. It does not support the add or
     * addAll operations.
     *
     * @return
     */
    public Collection<cacheNode> values() {
        lock.lock();
        try {
            Collection<cacheNode> values = myHash.values();
            return values;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of key-value mappings in this map. If the map contains
     * more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        lock.lock();
        try {
            return myHash.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @ returns a Set view of the mappings contained in this map. The set is
     * backed by the map, so changes to the map are reflected in the set, and
     * vice-versa. If the map is modified while an iteration over the set is in
     * progress (except through the iterator's own remove operation, or through
     * the setValue operation on a map entry returned by the iterator) the
     * results of the iteration are undefined. The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * Iterator.remove, Set.remove, removeAll, retainAll and clear operations.
     * It does not support the add or addAll operations.
     */
    public Set< Entry<Integer, cacheNode>> entrySet() {
        lock.lock();
        try {

            return myHash.entrySet();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes all of the mappings from this map (optional operation). The map
     * will be empty after this call returns.
     */
    public void clear() {
        lock.lock();
        try {
            myHash.clear();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns true if this map contains a mapping for the specified key. More
     * formally, returns true if and only if this map contains a mapping for a
     * key k such that (key==null ? k==null : key.equals(k)). (There can be at
     * most one such mapping.)
     *
     * @param key - key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public Boolean containsKey(Object key) {
        lock.lock();
        try {
            return myHash.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a string representation of the object. In general, the toString
     * method returns a string that "textually represents" this object. The
     * result should be a concise but informative representation that is easy
     * for a person to read. It is recommended that all subclasses override this
     * method. The toString method for class Object returns a string consisting
     * of the name of the class of which the object is an instance, the at-sign
     * character `@', and the unsigned hexadecimal representation of the hash
     * code of the object. In other words, this method returns a string equal to
     * the value of: getClass().getName() + '@' +
     * Integer.toHexString(hashCode())
     *  
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        lock.lock();
        try {
            return myHash.toString();
        } finally {
            lock.unlock();
        }
    }
}
