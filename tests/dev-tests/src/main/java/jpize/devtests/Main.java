package jpize.devtests;

import jpize.Jpize;
import jpize.io.context.ContextBuilder;

public class Main{
    
    public static void main(String[] args){
        //ContextBuilder.newContext(1300, 1300, "Dev-Test")
        //.newContext().register().setAdapter(new KeyboardTest());
        //.newContext().register().setAdapter(new MouseTest());
        //.newContext().register().setAdapter(new WindowTest());
        //.newContext().register().setAdapter(new MonitorTest());
        //.newContext().register().setAdapter(new FontTest());
        //.newContext().register().setAdapter(new QuadFromNormalTest());
        //.newContext().register().setAdapter(new TriangleIntersectionTest());
        //.newContext().register().setAdapter(new AtlasTest());
        //.newContext().register().setAdapter(new MultiThreadTest());
        //Jpize.runContexts();

        //myMultipleWindows();

        //new NeuralTest();
        //GlfwMultipleWindows.main(args);
        //LoadingTest.main(args);
        //GreedyMesh.main(args);
        MyGreedyMesh2D.main(args);
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
                .setAdapter(new FontTest());

        Jpize.runContexts();
    }
    
}
