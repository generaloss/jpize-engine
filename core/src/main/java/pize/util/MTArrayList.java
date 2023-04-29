package pize.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Multithreaded ArrayList
 **/
public class MTArrayList<T> implements Iterable<T>{

    private final ArrayList<T> arrayList;
    private final boolean duplicateLock;
    private boolean lock;

    public MTArrayList(){
        this(true);
    }

    public MTArrayList(boolean duplicateLock){
        arrayList = new ArrayList<>();
        this.duplicateLock = duplicateLock;
    }

    public int size(){
        return arrayList.size();
    }

    public T getNow(int index){
        return arrayList.get(index);
    }

    public T get(int index){
        waitForUnlock();
        T element = arrayList.get(index);
        lock = false;

        return element;
    }

    public T getLast(){
        if(arrayList.size() == 0)
            return null;

        waitForUnlock();
        T element = arrayList.get(arrayList.size() - 1);
        lock = false;

        return element;
    }

    public boolean contains(T target){
        waitForUnlock();
        boolean result = arrayList.contains(target);
        lock = false;
        return result;
    }

    public boolean add(T element){
        if(duplicateLock && arrayList.contains(element))
            return false;

        waitForUnlock();
        arrayList.add(element);
        lock = false;

        return true;
    }

    public void remove(T element){
        waitForUnlock();
        arrayList.remove(element);
        lock = false;
    }

    public void remove(int index){
        waitForUnlock();
        arrayList.remove(index);
        lock = false;
    }

    public void clear(){
        waitForUnlock();
        arrayList.clear();
        lock = false;
    }

    public void sort(Comparator<? super T> c){
        waitForUnlock();
        arrayList.sort(c);
        lock = false;
    }

    private void waitForUnlock(){
        while(lock)
            Utils.delayNanos(1000);
        lock = true;
    }

    @Override
    public Iterator<T> iterator(){
        waitForUnlock();
        Iterator<T> result = arrayList.iterator();
        lock = false;
        return result;
    }

}
