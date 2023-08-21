package pize.devtests;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.io.key.Key;
import pize.lib.gl.Gl;
import pize.lib.glfw.monitor.GlfwMonitor;

public class MonitorTest extends AppAdapter{

    TextureBatch batch;
    BitmapFont font;
    String info;

    public void init(){
        Pize.window().setSize(1280, 720);

        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);

        buildInfo();
    }

    private void buildInfo(){
        StringBuilder builder = new StringBuilder();
        GlfwMonitor monitor = Pize.monitor();

        builder.append("ID: " + monitor.getID() + "\n");
        builder.append("Name: " + monitor.getName() + "\n");
        builder.append("Size: " + monitor.getWidth() + "x" + monitor.getHeight() + "\n");
        builder.append("Refresh rate: " + monitor.getRefreshRate() + "\n");
        builder.append("Aspect ratio: " + monitor.getAspect() + "\n");
        builder.append("\n");
        builder.append("Bits per pixel: " + monitor.bitsPerPixel() + "\n");
        builder.append("R bits: " + monitor.getRedBits() + "\n");
        builder.append("G bits: " + monitor.getGreenBits() + "\n");
        builder.append("B bits: " + monitor.getBlueBits() + "\n");
        builder.append("\n");
        builder.append("Position: " + monitor.getPos() + "\n");
        builder.append("Physical size: " + monitor.getPhysicalSize() + "\n");
        builder.append("Content scale: " + monitor.getContentScale() + "\n");
        builder.append("Workarea: " + monitor.getWorkarea() + "\n");

        info = builder.toString();
    }

    public void render(){
        System.out.println(Pize.window().getKeyState(Key.L));

        if(Key.ESCAPE.isDown())
            Pize.exit();

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        batch.begin();
        font.drawText(batch, info, 0, Pize.getHeight(), Pize.getWidth(), true);
        batch.end();
    }

}
