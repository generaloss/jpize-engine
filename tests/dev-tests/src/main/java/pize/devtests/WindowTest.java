package pize.devtests;

import pize.Pize;
import pize.io.context.ContextAdapter;
import pize.gl.Gl;
import pize.glfw.key.Key;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.io.Window;
import pize.util.time.Stopwatch;

public class WindowTest extends ContextAdapter{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        Pize.window().setSize(1280, 720);

        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);

        Window window = Pize.window();

        window.setIconifyCallback((boolean iconify) -> {
            Stopwatch stopwatch = new Stopwatch().start();
            Pize.execIf(window::focus, () -> stopwatch.getSeconds() > 2);
        });
    }

    public void render(){
        Window window = Pize.window();

        window.requestAttention();

        if(Key.ESCAPE.isDown())
            Pize.exit();
        if(Key.F11.isDown())
            window.toggleFullscreen();

        if(Key.H.isDown()){
            window.hide();
            Stopwatch stopwatch = new Stopwatch().start();
            Pize.execIf(window::show, () -> stopwatch.getSeconds() > 2);
        }

        String info =
                "Clipboard string: " + window.getClipboardString() + "\n"
                + "Size: " + window.getSize() + "\n"
                + "Framebuffer size: " + window.getFramebufferSize() + "\n"
                + "Frame size: " + window.getFrameSize() + "\n"
                + "Content scale: " + window.getContentScale() + "\n"
                + "Monitor ID: " + window.getMonitorID() + "\n"
                + "Position: " + window.getPos() + "\n"
                + "\nPress H to hide window in 3 seconds";

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);
        batch.begin();
        font.drawText(batch, info, 0, window.getHeight(), window.getWidth(), true);
        batch.end();
    }

}
