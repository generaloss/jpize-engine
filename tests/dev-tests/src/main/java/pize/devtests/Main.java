package pize.devtests;

import pize.Jize;
import pize.io.context.ContextBuilder;

public class Main{
    
    public static void main(String[] args){
        ContextBuilder.newContext(1300, 1300, "Dev-Test")
        //.create().init(new KeyboardTest());
        //.create().init(new MouseTest());
        //.create().init(new WindowTest());
        //.create().init(new MonitorTest());
        //.create().init(new FontTest());
        //.create().init(new QuadFromNormalTest());
        .create().init(new TriangleIntersectionTest());
        //.create().init(new AtlasTest());
        //.create().init(new MultiThreadTest());
        Jize.runContexts();

        //myMultipleWindows();

        //new NeuralTest();
        //GlfwMultipleWindows.main(args);
        //LoadingTest.main(args);
    }

    private static void myMultipleWindows(){
        ContextBuilder.newContext("Window 1 (Quad Normal Test)")
                .size(1280, 720)
                .exitWhenWindowClose(false)
                .create()
                .init(new QuadFromNormalTest());

        ContextBuilder.newContext("Window 2 (Mouse Test)")
                .size(640, 480)
                .exitWhenWindowClose(false)
                .create()
                .init(new MouseTest());

        ContextBuilder.newContext("Window 3 (Font Test)")
                .size(720, 480)
                .exitWhenWindowClose(false)
                .create()
                .init(new FontTest());

        Jize.runContexts();
    }
    
}
