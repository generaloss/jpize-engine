package test;

import jpize.Jpize;
import jpize.io.context.ContextBuilder;

public class Main{
    
    public static void main(String[] args){
        ContextBuilder.newContext(1300, 1300, "Dev-Test")
        //.register().setAdapter(new KeyboardTest());
        //.register().setAdapter(new MouseTest());
        //.register().setAdapter(new WindowTest());
        //.register().setAdapter(new MonitorTest());
        //.register().setAdapter(new QuadFromNormalTest());
        //.register().setAdapter(new TriangleIntersectionTest());
        //.register().setAdapter(new AtlasTest());
        //.register().setAdapter(new MultiThreadTest());
        //.register().setAdapter(new MidiTest());
        //.register().setAdapter(new FontDemo());
        .register().setAdapter(new TextTest());
        Jpize.runContexts();

        //myMultipleWindows();

        //new NeuralTest();
        //GlfwMultipleWindows.main(args);
        //LoadingTest.main(args);
        //GreedyMesh.main(args);
        //MyGreedyMesh2D.main(args);
        //MyGreedyMesh3D.main(args);
    }

    private static void myMultipleWindows(){
        ContextBuilder.newContext("Window 1 (Quad Normal Test)")
                .size(1280, 720)
                .exitWhenWindowClose(false)
                .register()
                .setAdapter(new QuadFromNormalTest());

        ContextBuilder.newContext("Window 2 (Mouse Test)")
                .size(640, 480)
                .exitWhenWindowClose(false)
                .register()
                .setAdapter(new MouseTest());

        ContextBuilder.newContext("Window 3 (Font Test)")
                .size(720, 480)
                .exitWhenWindowClose(false)
                .register()
                .setAdapter(new TextTest());

        Jpize.runContexts();
    }
    
}
