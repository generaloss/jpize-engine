package jpize.util.array.list;

import java.util.Arrays;

public class DoubleList{

    private double[] array;
    private int size;

    public DoubleList(int capacity){
        if(capacity < 0)
            throw new IllegalArgumentException();
        this.array = new double[capacity];
    }

    public DoubleList(){
        this(2);
    }


    public double[] array(){
        return array;
    }

    public int size(){
        return size;
    }

    public int capacity(){
        return array.length;
    }

    private void grow(int minCapacity){
        if(array.length == 0)
            array = new double[minCapacity];
        else
            array = Arrays.copyOf(array, minCapacity);
    }

    private void grow(){
        grow(size + 1);
    }


    public void add(double element){
        if(size == array.length)
            grow();

        array[size] = element;
        size++;
    }

    public void add(double[] elements){
        if(size + elements.length >= array.length)
            grow(size + elements.length);

        System.arraycopy(elements, 0, array, size, elements.length);
        size += elements.length;
    }

    public void add(DoubleList list){
        add(list.array);
    }


    public void add(int i, double element){
        final int oldCapacity = array.length;
        if(size == array.length)
            grow();

        System.arraycopy(array, i, array, i + 1, oldCapacity - i);

        array[i] = element;
        size++;
    }

    public void add(int i, double[] elements){
        final int oldCapacity = array.length;
        if(size + elements.length >= oldCapacity)
            grow(size + elements.length);

        System.arraycopy(array, i, array, i + elements.length, oldCapacity - i);
        System.arraycopy(elements, 0, array, i, elements.length);
        size += elements.length;
    }


    public void remove(int i, int len){
        final int newCapacity = array.length - len;

        final double[] copy = new double[newCapacity];
        System.arraycopy(array, 0, copy, 0, i);
        System.arraycopy(array, i + len, copy, i, newCapacity - i);
        array = copy;

        size -= len;
    }

    public void remove(int i){
        remove(i, 1);
    }


    public boolean contains(double element){
        return indexOf(element) != -1;
    }

    public int indexOf(double element){
        return indexOfRange(element, 0, size);
    }

    public int lastIndexOf(double element){
        return lastIndexOfRange(element, 0, size);
    }

    public int indexOfRange(double element, int start, int end){
        for(int i = start; i < end; i++)
            if(array[i] == element)
                return i;
        return -1;
    }

    public int lastIndexOfRange(double element, int start, int end){
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
        Arrays.fill(array, 0, size, 0);
        size = 0;
    }


    public void trim(){
        array = Arrays.copyOf(array, size);
    }

    public double[] slice(int offset, int length){
        final double[] slice = new double[length];
        System.arraycopy(array, offset, slice, 0, length);
        return slice;
    }

    public double[] slice(int offset){
        return slice(offset, size - offset);
    }

    public void capacity(int newCapacity){
        if(newCapacity == 0)
            array = new double[0];
        else
            array = Arrays.copyOf(array, newCapacity);
        size = Math.min(size, newCapacity);
    }


    public double get(int i){
        return array[i];
    }

    public void set(int i, double newValue){
        array[i] = newValue;
    }

    public void valAdd(int i, double value){
        array[i] += value;
    }

    public void valSub(int i, double value){
        array[i] -= value;
    }

    public void valMul(int i, double value){
        array[i] *= value;
    }

    public void valDiv(int i, double value){
        array[i] /= value;
    }


    public void copyTo(double[] dst, int offset, int length){
        System.arraycopy(array, 0, dst, offset, length);
    }

    public void copyTo(double[] dst, int offset){
        copyTo(dst, offset, array.length);
    }

    public void copyTo(double[] dst){
        copyTo(dst, 0);
    }

}
