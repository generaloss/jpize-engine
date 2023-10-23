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
        ContextBuilder.newContext(1280, 720, "Window 1 (Quad Normal Test)")
                .exitWhenWindowClose(false)
                .register()
                .setAdapter(new QuadFromNormalTest());

        ContextBuilder.newContext(640, 480, "Window 2 (Mouse Test)")
                .exitWhenWindowClose(false)
                .register()
                .setAdapter(new MouseTest());

        ContextBuilder.newContext(720, 480, "Window 3 (Font Test)")
                .exitWhenWindowClose(false)
                .register()
                .setAdapter(new TextTest());

        Jpize.exitOnWindowsClosed(true);
        Jpize.runContexts();
    }
    
}
