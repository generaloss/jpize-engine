package test;

import jpize.util.array.list.FloatList;
import jpize.util.time.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveListTest{

    public static void main(String[] args){
        Stopwatch sw = new Stopwatch().start();
        FloatList a = new FloatList();
        List<Float> l = new ArrayList<>();

        sw.reset();
        for(float i = 0; i < 100000; i++)
            a.add(i);
        System.out.println("a: " + sw.getMillis());

        sw.reset();
        for(float i = 0; i < 100000; i++)
            l.add(i);
        System.out.println("l: " + sw.getMillis());
    }

}
