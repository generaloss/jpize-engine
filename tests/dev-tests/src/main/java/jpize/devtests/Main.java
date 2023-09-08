package jpize.devtests;

import jpize.Jpize;
import jpize.io.context.ContextBuilder;

public class Main{
    
    public static void main(String[] args){
        //ContextBuilder.newContext(1300, 1300, "Dev-Test")
        //.create().init(new KeyboardTest());
        //.create().init(new MouseTest());
        //.create().init(new WindowTest());
        //.create().init(new MonitorTest());
        //.create().init(new FontTest());
        //.create().init(new QuadFromNormalTest());
        //.create().init(new TriangleIntersectionTest());
        //.create().init(new AtlasTest());
        //.create().init(new MultiThreadTest());
        //Jpize.runContexts();

        //myMultipleWindows();

        //new NeuralTest();
        //GlfwMultipleWindows.main(args);
        //LoadingTest.main(args);
        GreedyMesh.main(args);
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
                .setAdapter(new FontTest());

        Jpize.runContexts();
    }
    
}
