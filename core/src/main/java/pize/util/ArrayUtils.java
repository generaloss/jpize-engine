package pize.util;

import java.util.List;

public class ArrayUtils{

    public static boolean contains(int[] arr, int e){
        for(int i: arr)
            if(i == e)
                return true;
        
        return false;
    }

    public static float[] add(float[] a, float[] b){
        float[] c = new float[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        
        return c;
    }

    public static float[] add(float[] a, float[]... b){
        float[] c = add(a, b[0]);
        for(int i = 1; i < b.length; i++)
            c = add(c, b[i]);
        
        return c;
    }

    public static float[] fromList(List<Float> list){
        final float[] array = new float[list.size()];
        for(int i = 0; i < array.length; i++)
            array[i] = list.get(i);

        return array;
    }

}
