package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.Font;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.sdl.window.SdlWindow;
import jpize.app.JpizeApplication;
import jpize.sdl.input.Key;
import jpize.sdl.window.SdlFlashOp;
import jpize.util.time.Stopwatch;

public class WindowTest extends JpizeApplication{

    TextureBatch batch;
    Font font;

    public void init(){
        Jpize.window().setSize(1280, 720);
        Gl.clearColor(0.4, 0.5, 0.7);

        batch = new TextureBatch();

        font = FontLoader.loadFnt("fonts/font.fnt");
        font.setScale(0.5F);
        font.options.invLineWrap = true;

        SdlWindow window = Jpize.window();

        // window.setIconifyCallback((boolean iconify) -> {
        //     Stopwatch stopwatch = new Stopwatch().start();
        //     Jpize.execIf(window::focus, () -> stopwatch.getSeconds() > 2);
        // });
    }

    public void update(){
        SdlWindow window = Jpize.window();

        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            window.toggleFullscreenDesktop();
        if(Key.A.isDown())
            window.flash(SdlFlashOp.FLASH_BRIEFLY);

        if(Key.H.isDown()){
            window.hide();
            Stopwatch stopwatch = new Stopwatch().start();
            Jpize.execIf(window::show, () -> stopwatch.getSeconds() > 2);
        }

        font.options.textAreaWidth = Jpize.getWidth();
    }

    public void render(){
        SdlWindow window = Jpize.window();
        String info =
//                "Clipboard string: " + window.getClipboardString() + "\n" +
                "Size: " + window.getSize() + "\n"
//                + "Framebuffer size: " + window.getFramebufferSize() + "\n"
//                + "Frame size: " + window.getFrameSize() + "\n"
//                + "Content scale: " + window.getContentScale() + "\n"
//                + "Monitor ID: " + window.getMonitorID() + "\n"
                + "Position: " + window.getPos() + "\n"
                + "\nPress H to hide window in 3 seconds"
                + "\nPress A to require attention";

        Gl.clearColorBuffer();
        batch.begin();
        font.drawText(batch, info, 0, window.getHeight());
        batch.end();
    }

}
