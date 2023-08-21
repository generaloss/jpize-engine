package pize.devtests;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.io.key.Key;
import pize.lib.gl.Gl;
import pize.lib.glfw.mouse.GlfwCursorMode;

public class MouseTest extends AppAdapter{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        Pize.window().setSize(1280, 720);

        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);


    }

    public void render(){
        if(Key.ESCAPE.isDown())
            Pize.exit();

        if(Key.C.isDown()) Pize.mouse().toCenter();
        if(Key.NUM_1.isDown()) Pize.mouse().setMode(GlfwCursorMode.NORMAL);
        if(Key.NUM_2.isDown()) Pize.mouse().setMode(GlfwCursorMode.HIDDEN);
        if(Key.NUM_3.isDown()) Pize.mouse().setMode(GlfwCursorMode.DISABLED);

        String info =
                  "X: " + Pize.getX() + ", Y: " + Pize.getY() + "\n"
                + "Mode (Num 1/2/3): " + Pize.mouse().getMode() + "\n"
                + "Scroll X/Y: " + Pize.mouse().getScrollX() + "/" + Pize.mouse().getScrollY() + "\n"
                + "Left button: " + Pize.mouse().getMouseButton(Key.MOUSE_LEFT) + "\n"
                + "To center - C";

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        batch.begin();
        font.drawText(batch, info, 0, Pize.getHeight(), Pize.getWidth(), true);
        batch.end();
    }

}
