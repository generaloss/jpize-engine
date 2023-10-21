package test;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.glfw.key.Key;
import jpize.gl.Gl;
import jpize.glfw.monitor.GlfwMonitor;

public class MonitorTest extends JpizeApplication{

    TextureBatch batch;
    BitmapFont font;
    String info;

    public void init(){
        Jpize.window().setSize(1280, 720);

        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);
        font.getOptions().textAreaWidth = Jpize.getWidth();
        font.getOptions().invLineWrap = true;

        buildInfo();
    }

    private void buildInfo(){
        StringBuilder builder = new StringBuilder();
        GlfwMonitor monitor = Jpize.monitor();

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
        if(Key.ESCAPE.isDown())
            Jpize.exit();

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        batch.begin();
        font.drawText(batch, info, 0, Jpize.getHeight());
        batch.end();
    }

}
