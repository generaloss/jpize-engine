package pize.devtests;

import pize.Jize;
import pize.io.context.ContextAdapter;
import pize.gl.Gl;
import pize.glfw.key.Key;
import pize.glfw.key.MBtn;
import pize.glfw.mouse.cursor.GlfwCursor;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;

import java.util.StringJoiner;

public class MouseTest extends ContextAdapter{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);

        GlfwCursor cursor = new GlfwCursor("texture15.png");
        Jize.mouse().setCursor(cursor);
    }

    public void render(){
        if(Key.S.isDown())
            Jize.mouse().setStickyButtons(true);

        if(Key.ESCAPE.isDown())
            Jize.exit();
        if(Key.F11.isDown())
            Jize.window().toggleFullscreen();

        if(Key.C.isDown()) Jize.mouse().toCenter();

        if(Key.NUM_1.isDown()) Jize.mouse().show();
        if(Key.NUM_2.isDown()) Jize.mouse().hide();
        if(Key.NUM_3.isDown()) Jize.mouse().disable();

        String info =
                  "X: " + Jize.getX() + ", Y: " + Jize.getY() + "\n"
                + "Mode (Num 1/2/3): " + Jize.mouse().glfwMouse().getCursorMode() + "\n"
                + "Scroll X/Y: " + Jize.mouse().getScrollX() + "/" + Jize.mouse().getScroll() + "\n"
                + "To center - C";

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        batch.begin();

        font.drawText(batch, info, 0, Jize.getHeight(), Jize.getWidth(), true);

        StringJoiner pressedKeys = new StringJoiner(", ");
        for(MBtn button: MBtn.values())
            if(button.isPressed())
                pressedKeys.add(button.getName());
        font.drawText(batch, "Pressed buttons: " + pressedKeys, 0, 0, Jize.getWidth(), false);

        batch.end();
    }

}
