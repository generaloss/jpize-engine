package jpize.util.array;

import java.util.Arrays;

public class ShortList{

    public static final int DEFAULT_CAPACITY = 10;

    private short[] array;
    private int size;

    public ShortList(ShortList list){
        this.array = new short[list.array.length];
        this.size = list.size;
        list.copyTo(this.array);
    }

    public ShortList(int capacity){
        if(capacity < 0)
            throw new IllegalArgumentException();
        this.array = new short[capacity];
    }

    public ShortList(){
        this(DEFAULT_CAPACITY);
    }


    public short[] array(){
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
            array = new short[Math.max(minCapacity, DEFAULT_CAPACITY)];
        }else{
            final int newCapacity = ArraysSupport.newLength(oldCapacity, minCapacity - oldCapacity, oldCapacity >> 1);
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    private void grow(){
        grow(size + 1);
    }


    public void add(short element){
        if(size == array.length)
            grow();

        array[size] = element;
        size++;
    }

    public void add(short[] elements){
        if(size + elements.length >= array.length)
            grow(size + elements.length);

        System.arraycopy(elements, 0, array, size, elements.length);
        size += elements.length;
    }

    public void add(ShortList list){
        add(list.array);
    }


    public void add(int i, short element){
        final int oldCapacity = array.length;
        if(size == array.length)
            grow();

        System.arraycopy(array, i, array, i + 1, oldCapacity - i);

        array[i] = element;
        size++;
    }

    public void add(int i, short[] elements){
        final int oldCapacity = array.length;
        if(size + elements.length >= oldCapacity)
            grow(size + elements.length);

        System.arraycopy(array, i, array, i + elements.length, oldCapacity - i);
        System.arraycopy(elements, 0, array, i, elements.length);
        size += elements.length;
    }


    public void remove(int i, int len){
        final int newCapacity = array.length - len;

        final short[] copy = new short[newCapacity];
        System.arraycopy(array, 0, copy, 0, i);
        System.arraycopy(array, i + len, copy, i, newCapacity - i);
        array = copy;

        size -= len;
    }

    public void remove(int i){
        remove(i, 1);
    }


    public boolean contains(short element){
        return indexOf(element) != -1;
    }

    public int indexOf(short element){
        return indexOfRange(element, 0, size);
    }

    public int lastIndexOf(short element){
        return lastIndexOfRange(element, 0, size);
    }

    public int indexOfRange(short element, int start, int end){
        for(int i = start; i < end; i++)
            if(array[i] == element)
                return i;
        return -1;
    }

    public int lastIndexOfRange(short element, int start, int end){
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
        Arrays.fill(array, 0, size, (short) 0);
        size = 0;
    }


    public void trim(){
        array = Arrays.copyOf(array, size);
    }

    public short[] slice(int offset, int length){
        final short[] slice = new short[length];
        System.arraycopy(array, offset, slice, 0, length);
        return slice;
    }

    public short[] slice(int length){
        return slice(0, length);
    }

    public void capacity(int newCapacity){
        if(newCapacity == 0)
            array = new short[0];
        else
            array = Arrays.copyOf(array, newCapacity);
        size = Math.min(size, newCapacity);
    }


    public short get(int i){
        return array[i];
    }

    public void set(int i, short newValue){
        array[i] = newValue;
    }

    public void valAdd(int i, short value){
        array[i] += value;
    }

    public void valSub(int i, short value){
        array[i] -= value;
    }

    public void valMul(int i, short value){
        array[i] *= value;
    }

    public void valDiv(int i, short value){
        array[i] /= value;
    }


    public void copyTo(short[] dst, int offset, int length){
        System.arraycopy(array, 0, dst, offset, length);
    }

    public void copyTo(short[] dst, int offset){
        copyTo(dst, offset, array.length);
    }

    public void copyTo(short[] dst){
        copyTo(dst, 0);
    }


    public ShortList copy(){
        return new ShortList(this);
    }

}
