package jpize.tests.mcose.launcher;

import jpize.Jpize;
import jpize.io.context.ContextBuilder;

import javax.swing.*;
import java.awt.*;

public class Main{

    private static final int width = 720;
    private static final int height = 480;
    private static final String title = "MCOSE Launcher";

    public static void main(String[] args){
        // runJPize();
        // runCli();
        runJFrame();
    }

    private static void runJPize(){
        ContextBuilder.newContext(width, height, title)
            .register().setAdapter(new MoseLauncherJpize());

        Jpize.runContexts();
    }

    private static void runCli(){
        new MoseLauncherCli();
    }

    private static void runJFrame(){
        final MoseLauncherSwing jFrame = new MoseLauncherSwing();

        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        jFrame.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);

        jFrame.setUndecorated(true);
        jFrame.setSize(width, height);
        jFrame.setTitle(title);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

}
