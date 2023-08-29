package pize.devtests;

import pize.Jize;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.io.context.ContextAdapter;
import pize.io.context.ContextBuilder;
import pize.util.time.PizeRunnable;

public class LoadingTest{

    public static void main(String[] args){
        ContextBuilder
                .newContext(640, 360)
                .borderless(true)
                .exitWhenWindowClose(false)
                .create().init(new LoadingWindow());

        new PizeRunnable(() ->
                Jize.execSync(() -> {

                    Jize.closeAllWindows();
                    ContextBuilder.newContext(1280, 720, "App")
                            .create().init(new KeyboardTest());
                })
        ).runLaterAsync(1500);

        Jize.runContexts();
    }

    static class LoadingWindow extends ContextAdapter{
        public void init(){
            final Texture bg = new Texture("wallpaper-16.jpg");
            final TextureBatch batch = new TextureBatch();
            final BitmapFont font = FontLoader.getDefault();

            batch.begin();
            batch.draw(bg, 0, 0, Jize.getWidth(), Jize.getHeight());
            font.drawText(batch, "Loading...", 20, 20);
            batch.end();
        }
    }

}
