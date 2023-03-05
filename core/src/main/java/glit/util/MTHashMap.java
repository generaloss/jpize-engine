package glit.util;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Multithreaded HashMap
 **/
public class MTHashMap<K, V>{

    private final ConcurrentHashMap<K, V> hashMap;
    private boolean locked;

    public MTHashMap(){
        hashMap = new ConcurrentHashMap<>();
    }

    public void put(K key, V value){
        waitForUnlock();
        hashMap.put(key, value);
        locked = false;
    }

    public void remove(K key){
        waitForUnlock();
        hashMap.remove(key);
        locked = false;
    }

    public V get(K key){
        waitForUnlock();
        V value = hashMap.get(key);
        locked = false;
        return value;
    }

    public boolean containsKey(K key){
        waitForUnlock();
        boolean value = hashMap.containsKey(key);
        locked = false;
        return value;
    }

    public Set<K> keySet(){
        waitForUnlock();
        Set<K> value = hashMap.keySet();
        locked = false;
        return value;
    }

    public Collection<V> values(){
        waitForUnlock();
        Collection<V> value = hashMap.values();
        locked = false;
        return value;
    }

    private void waitForUnlock(){
        while(locked)
            Utils.delayNanos(1000);
        locked = true;
    }

    public int sizeNow(){
        return hashMap.size();
    }

}
