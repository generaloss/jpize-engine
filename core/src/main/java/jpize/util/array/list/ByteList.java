package jpize.util.array.list;

import jpize.util.array.ArraysSupport;

import java.util.Arrays;

public class ByteList{

    public static final int DEFAULT_CAPACITY = 10;

    private byte[] array;
    private int size;

    public ByteList(int capacity){
        if(capacity < 0)
            throw new IllegalArgumentException();
        this.array = new byte[capacity];
    }

    public ByteList(){
        this(DEFAULT_CAPACITY);
    }


    public byte[] array(){
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
            array = new byte[Math.max(minCapacity, DEFAULT_CAPACITY)];
        }else{
            final int newCapacity = ArraysSupport.newLength(oldCapacity, minCapacity - oldCapacity, oldCapacity >> 1);
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    private void grow(){
        grow(size + 1);
    }


    public void add(byte element){
        if(size == array.length)
            grow();

        array[size] = element;
        size++;
    }

    public void add(byte[] elements){
        if(size + elements.length >= array.length)
            grow(size + elements.length);

        System.arraycopy(elements, 0, array, size, elements.length);
        size += elements.length;
    }

    public void add(ByteList list){
        add(list.array);
    }


    public void add(int i, byte element){
        final int oldCapacity = array.length;
        if(size == array.length)
            grow();

        System.arraycopy(array, i, array, i + 1, oldCapacity - i);

        array[i] = element;
        size++;
    }

    public void add(int i, byte[] elements){
        final int oldCapacity = array.length;
        if(size + elements.length >= oldCapacity)
            grow(size + elements.length);

        System.arraycopy(array, i, array, i + elements.length, oldCapacity - i);
        System.arraycopy(elements, 0, array, i, elements.length);
        size += elements.length;
    }


    public void remove(int i, int len){
        final int newCapacity = array.length - len;

        final byte[] copy = new byte[newCapacity];
        System.arraycopy(array, 0, copy, 0, i);
        System.arraycopy(array, i + len, copy, i, newCapacity - i);
        array = copy;

        size -= len;
    }

    public void remove(int i){
        remove(i, 1);
    }


    public boolean contains(byte element){
        return indexOf(element) != -1;
    }

    public int indexOf(byte element){
        return indexOfRange(element, 0, size);
    }

    public int lastIndexOf(byte element){
        return lastIndexOfRange(element, 0, size);
    }

    public int indexOfRange(byte element, int start, int end){
        for(int i = start; i < end; i++)
            if(array[i] == element)
                return i;
        return -1;
    }

    public int lastIndexOfRange(byte element, int start, int end){
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
        Arrays.fill(array, 0, size, (byte) 0);
        size = 0;
    }


    public void trim(){
        array = Arrays.copyOf(array, size);
    }

    public byte[] slice(int offset, int length){
        final byte[] slice = new byte[length];
        System.arraycopy(array, offset, slice, 0, length);
        return slice;
    }

    public byte[] slice(int offset){
        return slice(offset, size - offset);
    }

    public void capacity(int newCapacity){
        if(newCapacity == 0)
            array = new byte[0];
        else
            array = Arrays.copyOf(array, newCapacity);
        size = Math.min(size, newCapacity);
    }


    public byte get(int i){
        return array[i];
    }

    public void set(int i, byte newValue){
        array[i] = newValue;
    }

    public void valAdd(int i, byte value){
        array[i] += value;
    }

    public void valSub(int i, byte value){
        array[i] -= value;
    }

    public void valMul(int i, byte value){
        array[i] *= value;
    }

    public void valDiv(int i, byte value){
        array[i] /= value;
    }


    public void copyTo(byte[] dst, int offset, int length){
        System.arraycopy(array, 0, dst, offset, length);
    }

    public void copyTo(byte[] dst, int offset){
        copyTo(dst, offset, array.length);
    }

    public void copyTo(byte[] dst){
        copyTo(dst, 0);
    }

}
