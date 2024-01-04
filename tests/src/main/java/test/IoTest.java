package test;

import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class IoTest{

    public static void main(String[] args) throws Throwable{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        JpizeOutputStream out = new JpizeOutputStream(bytes);
        out.writeLongArray(new long[]{3452095223453535353L, -109735460983535353L});

        out.close();

        JpizeInputStream in = new JpizeInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        System.out.println(Arrays.toString(in.readLongArray()));
    }

}
