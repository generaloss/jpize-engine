package pize.devtests;

import pize.Pize;

public class Main{
    
    public static void main(String[] args){
        //new NeuralTest();
        Pize.create("Dev-Test", 1300, 1300);
        Pize.run(new MouseTest());
        //Pize.run(new MonitorTest());
        //Pize.run(new FontTest());
        //Pize.run(new QuadFromNormalTest());
        //Pize.run(new AtlasTest());
    }
    
}
