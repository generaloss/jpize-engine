package test;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.Window;
import jpize.util.time.Stopwatch;

public class WindowTest extends JpizeApplication{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        Jpize.window().setSize(1280, 720);

        batch = new TextureBatch();

        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);
        font.getOptions().textAreaWidth = Jpize.getWidth();
        font.getOptions().invLineWrap = true;

        Window window = Jpize.window();

        window.setIconifyCallback((boolean iconify) -> {
            Stopwatch stopwatch = new Stopwatch().start();
            Jpize.execIf(window::focus, () -> stopwatch.getSeconds() > 2);
        });
    }

    public void render(){
        Window window = Jpize.window();

        window.requestAttention();

        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            window.toggleFullscreen();

        if(Key.H.isDown()){
            window.hide();
            Stopwatch stopwatch = new Stopwatch().start();
            Jpize.execIf(window::show, () -> stopwatch.getSeconds() > 2);
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
        font.drawText(batch, info, 0, window.getHeight());
        batch.end();
    }

}
