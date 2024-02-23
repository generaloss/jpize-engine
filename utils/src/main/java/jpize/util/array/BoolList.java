package jpize.util.array;

import java.util.Arrays;

public class BoolList{

    public static final int DEFAULT_CAPACITY = 10;

    private boolean[] array;
    private int size;

    public BoolList(BoolList list){
        this.array = new boolean[list.array.length];
        this.size = list.size;
        list.copyTo(this.array);
    }

    public BoolList(int capacity){
        if(capacity < 0)
            throw new IllegalArgumentException();
        this.array = new boolean[capacity];
    }

    public BoolList(){
        this(DEFAULT_CAPACITY);
    }


    public boolean[] array(){
        return array;
    }

    public int size(){
        return size;
    }

    public int capacity(){
        return array.length;
    }

    private void grow(int minCapacity){
        final int oldCapacity = array.length;
        if(oldCapacity == 0){
            array = new boolean[Math.max(minCapacity, DEFAULT_CAPACITY)];
        }else{
            final int newCapacity = ArraysSupport.newLength(oldCapacity, minCapacity - oldCapacity, oldCapacity >> 1);
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    private void grow(){
        grow(size + 1);
    }


    public void add(boolean element){
        if(size == array.length)
            grow();

        array[size] = element;
        size++;
    }

    public void add(boolean[] elements){
        if(size + elements.length >= array.length)
            grow(size + elements.length);

        System.arraycopy(elements, 0, array, size, elements.length);
        size += elements.length;
    }

    public void add(BoolList list){
        add(list.array);
    }


    public void add(int i, boolean element){
        final int oldCapacity = array.length;
        if(size == array.length)
            grow();

        System.arraycopy(array, i, array, i + 1, oldCapacity - i);

        array[i] = element;
        size++;
    }

    public void add(int i, boolean[] elements){
        final int oldCapacity = array.length;
        if(size + elements.length >= oldCapacity)
            grow(size + elements.length);

        System.arraycopy(array, i, array, i + elements.length, oldCapacity - i);
        System.arraycopy(elements, 0, array, i, elements.length);
        size += elements.length;
    }


    public void remove(int i, int len){
        final int newCapacity = array.length - len;

        final boolean[] copy = new boolean[newCapacity];
        System.arraycopy(array, 0, copy, 0, i);
        System.arraycopy(array, i + len, copy, i, newCapacity - i);
        array = copy;

        size -= len;
    }

    public void remove(int i){
        remove(i, 1);
    }


    public boolean contains(boolean element){
        return indexOf(element) != -1;
    }

    public int indexOf(boolean element){
        return indexOfRange(element, 0, size);
    }

    public int lastIndexOf(boolean element){
        return lastIndexOfRange(element, 0, size);
    }

    public int indexOfRange(boolean element, int start, int end){
        for(int i = start; i < end; i++)
            if(array[i] == element)
                return i;
        return -1;
    }

    public int lastIndexOfRange(boolean element, int start, int end){
        for(int i = end - 1; i >= start; i--)
            if(array[i] == element)
                return i;
        return -1;
    }


    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isNotEmpty(){
        return size != 0;
    }

    public void clear(){
        Arrays.fill(array, 0, size, false);
        size = 0;
    }


    public void trim(){
        array = Arrays.copyOf(array, size);
    }

    public boolean[] slice(int offset, int length){
        final boolean[] slice = new boolean[length];
        System.arraycopy(array, offset, slice, 0, length);
        return slice;
    }

    public boolean[] slice(int length){
        return slice(0, length);
    }

    public void capacity(int newCapacity){
        if(newCapacity == 0)
            array = new boolean[0];
        else
            array = Arrays.copyOf(array, newCapacity);
        size = Math.min(size, newCapacity);
    }


    public boolean get(int i){
        return array[i];
    }

    public void set(int i, boolean newValue){
        array[i] = newValue;
    }


    public void copyTo(boolean[] dst, int offset, int length){
        System.arraycopy(array, 0, dst, offset, length);
    }

    public void copyTo(boolean[] dst, int offset){
        copyTo(dst, offset, array.length);
    }

    public void copyTo(boolean[] dst){
        copyTo(dst, 0);
    }


    public BoolList copy(){
        return new BoolList(this);
    }

}
