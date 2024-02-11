package test;

import jpize.util.res.Resource;

import java.util.Arrays;

public class ResourceTest{

    public static void main(String[] args){
        System.out.println(Arrays.toString(Resource.internal(".").listRes()));
        System.out.println(Arrays.toString(Resource.external(".").listRes()));
    }

}
