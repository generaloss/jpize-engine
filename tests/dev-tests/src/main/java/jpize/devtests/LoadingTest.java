package jpize.devtests;

import jpize.Jpize;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextAdapter;
import jpize.io.context.ContextBuilder;
import jpize.util.time.JpizeRunnable;

public class LoadingTest{

    public static void main(String[] args){
        ContextBuilder
                .newContext(640, 360)
                .borderless(true)
                .exitWhenWindowClose(false)
                .create().init(new LoadingWindow());

        new JpizeRunnable(() ->
                Jpize.execSync(() -> {

                    Jpize.closeAllWindows();
                    ContextBuilder.newContext(1280, 720, "App")
                            .create().init(new KeyboardTest());
                })
        ).runLaterAsync(1500);

        Jpize.runContexts();
    }

    static class LoadingWindow extends ContextAdapter{
        public void init(){
            final Texture bg = new Texture("wallpaper-16.jpg");
            final TextureBatch batch = new TextureBatch();
            final BitmapFont font = FontLoader.getDefault();

            batch.begin();
            batch.draw(bg, 0, 0, Jpize.getWidth(), Jpize.getHeight());
            font.drawText(batch, "Loading...", 20, 20);
            batch.end();
        }
    }

}
