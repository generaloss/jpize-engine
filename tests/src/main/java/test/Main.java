package test;

import jpize.Jpize;
import jpize.io.context.ContextBuilder;

public class Main{
    
    public static void main(String[] args) throws Throwable{
        Thread.currentThread().setName("Main-Thread");

        //myMultipleWindows();

        //SdlTest.main(args);
        //ResourceTest.main(args);
        //NeuralTest.main(args);
        //LoadingTest.main(args);
        //GreedyMesh.main(args);
        //MyGreedyMesh2D.main(args);
        //MyGreedyMesh3D.main(args);
        //IoTest.main(args);
        //SdlWindowTest.main(args);
        //OpenGLTest.main(args);

        // System.exit(0);

        ContextBuilder.newContext("Dev-Test", 900, 900).icon("textures/texture8.png")
        //.register().setAdapter(new KeyboardTest());
        //.register().setAdapter(new MouseTest());
        //.register().setAdapter(new WindowTest());
        //.register().setAdapter(new QuadFromNormalTest());
        //.register().setAdapter(new AtlasTest());
        //.register().setAdapter(new MidiTest());
        //.register().setAdapter(new FontDemo());
        //.register().setAdapter(new TextTest());
        //.register().setAdapter(new TextEditor());
        //.register().setAdapter(new AudioTest());
        //.register().setAdapter(new UiTest());
        //.register().setAdapter(new ListTest());
        //.register().setAdapter(new ResTest());
        //.register().setAdapter(new TilemapTest());
        .register().setAdapter(new VecMathTest());
        //.register().setAdapter(new TriangleIntersectionTest());
        Jpize.runContexts();
    }

    private static void myMultipleWindows(){
        ContextBuilder.newContext("Window 1 (Triangle Intersection Test)", 1280, 720)
            .exitWhenWindowClose(false)
            .position(0, 0)
            .register()
            .setAdapter(new TriangleIntersectionTest());

        ContextBuilder.newContext("Window 2 (Mouse Test)", 640, 480)
            .exitWhenWindowClose(false)
            .position(1280, 0)
            .register()
            .setAdapter(new MouseTest());

        ContextBuilder.newContext("Window 3 (Text Test)", 720, 480)
            .exitWhenWindowClose(false)
            .position(0, 720)
            .register()
            .setAdapter(new TextTest());

        Jpize.exitOnWindowsClosed(true);
        Jpize.runContexts();
    }
    
}
