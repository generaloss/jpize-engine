package test;

import jpize.Jpize;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.JpizeApplication;
import jpize.io.context.ContextBuilder;
import jpize.util.Utils;

public class LoadingTest{

    public static void main(String[] args){
        // Run Loading
        ContextBuilder.newContext(640, 360, "Loading App...")
            .borderless(true)
            .exitWhenWindowClose(false)
            .register().setAdapter(new LoadingWindow());

        Jpize.runContexts();
    }

    static class LoadingWindow extends JpizeApplication{
        // Image, font
        final TextureBatch batch = new TextureBatch();
        final Texture splash = new Texture("background.jpg");
        final BitmapFont font = FontLoader.getDefault();

        public void init(){
            // Render loading window
            batch.setColor(1, 0.8, 0.9);
            batch.begin();

            batch.draw(splash, 0, 0, Jpize.getWidth(), Jpize.getHeight());
            font.drawText(batch, "Loading...", 20, 20);

            batch.end();
            splash.dispose();
            batch.dispose();

            // Run App
            Jpize.execSync(() ->
                ContextBuilder.newContext(1280, 720, "App")
                    .register()
                    .setAdapter(new App())
            );
        }
    }

    static class App extends JpizeApplication{
        public void init(){ // Constructor() or init()
            // *Loading resources*
            Utils.delayMillis(2000);
            // Close loading window
            Jpize.closeOtherWindows();
        }
    }

}
