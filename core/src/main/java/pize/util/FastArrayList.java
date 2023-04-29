package pize.util;

public class FastArrayList<T>{

    private Object[] list;
    private int size;

    public FastArrayList(){
        this(16);
    }

    public FastArrayList(int size){
        list = new Object[size];
    }

    public void add(T element){
        if(checkIfArrayFull())
            copyArray(true);

        list[size] = element;
        size++;
    }

    public void set(int index, T element){
        if(index >= list.length)
            return;
        list[index] = element;
    }

    public void add(int index, T element){
        if(checkIfArrayFull())
            copyArray(true);

        if(index >= list.length)
            return;

        Object temp = list[index];
        list[index] = element;

        Object temp2;
        for(int i = index; i < list.length - 1; i++){
            temp2 = list[i + 1];
            list[i + 1] = temp;
            temp = temp2;
        }

        copyArray(false);
        size++;
    }

    public T get(int index){
        return (T) list[index];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean contains(Object element){
        return find(element) >= 0;
    }

    public int find(Object target){
        if(target == null)
            return -1;
        for(int i = 0; i < list.length; i++)
            if(target.equals(list[i]))
                return i;
        return -1;
    }

    public void remove(T element){
        for(int i = 0; i < size; i++){
            if(element.equals(list[i])){
                list[i] = null;
                size--;
                copyArray(false);
                return;
            }
        }
    }

    public void remove(int index){
        list[index] = null;
        size--;
        copyArray(false);
    }

    public void clear(){
        for(int i = 0; i < size; i++)
            list[i] = null;
        list = new Object[10];
        size = 0;
    }

    private boolean checkIfArrayFull(){
        return list.length == size;
    }

    private void copyArray(boolean doubleIncrease){
        Object[] tempArray = new Object[list.length * (doubleIncrease ? 2 : 1)];

        int tempElement = 0;
        for(int i = 0; i < list.length; i++, tempElement++){
            if(list[i] == null){
                tempElement--;
                continue;
            }
            tempArray[tempElement] = list[i];
        }

        list = tempArray;
    }

}