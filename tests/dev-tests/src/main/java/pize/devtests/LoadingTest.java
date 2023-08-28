package pize.devtests;

import pize.Pize;
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
                Pize.execSync(() -> {

                    Pize.closeAllWindows();
                    ContextBuilder.newContext(1280, 720, "App")
                            .create().init(new KeyboardTest());
                })
        ).runLaterAsync(1500);

        Pize.runContexts();
    }

    static class LoadingWindow extends ContextAdapter{
        public void init(){
            final Texture bg = new Texture("wallpaper-16.jpg");
            final TextureBatch batch = new TextureBatch();
            final BitmapFont font = FontLoader.getDefault();

            batch.begin();
            batch.draw(bg, 0, 0, Pize.getWidth(), Pize.getHeight());
            font.drawText(batch, "Loading...", 20, 20);
            batch.end();
        }
    }

}
