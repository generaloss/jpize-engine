package test;

import jpize.files.Resource;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.util.Arrays;

public class IoTest{

    public static void main(String[] args) throws Throwable{
        Resource res = new Resource("pizza", true);
        res.create();

        JpizeOutputStream out = res.getJpizeOut();
        out.writeLongArray(new long[]{3452095223453535353L, -109735460983535353L});

        out.close();

        JpizeInputStream in = res.getJpizeIn();
        System.out.println(Arrays.toString(in.readLongArray()));
    }

}
