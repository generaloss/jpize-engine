package jpize.devtests;

import jpize.Jpize;
import jpize.io.context.ContextAdapter;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.glfw.key.MBtn;
import jpize.glfw.mouse.cursor.GlfwCursor;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;

import java.util.StringJoiner;

public class MouseTest extends ContextAdapter{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);

        GlfwCursor cursor = new GlfwCursor("texture15.png");
        Jpize.mouse().setCursor(cursor);
    }

    public void render(){
        if(Key.S.isDown())
            Jpize.mouse().setStickyButtons(true);

        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreen();

        if(Key.C.isDown()) Jpize.mouse().toCenter();

        if(Key.NUM_1.isDown()) Jpize.mouse().show();
        if(Key.NUM_2.isDown()) Jpize.mouse().hide();
        if(Key.NUM_3.isDown()) Jpize.mouse().disable();

        String info =
                  "X: " + Jpize.getX() + ", Y: " + Jpize.getY() + "\n"
                + "Mode (Num 1/2/3): " + Jpize.mouse().glfwMouse().getCursorMode() + "\n"
                + "Scroll X/Y: " + Jpize.mouse().getScrollX() + "/" + Jpize.mouse().getScroll() + "\n"
                + "To center - C";

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        batch.begin();

        font.drawText(batch, info, 0, Jpize.getHeight(), Jpize.getWidth(), true);

        StringJoiner pressedKeys = new StringJoiner(", ");
        for(MBtn button: MBtn.values())
            if(button.isPressed())
                pressedKeys.add(button.getName());
        font.drawText(batch, "Pressed buttons: " + pressedKeys, 0, 0, Jpize.getWidth(), false);

        batch.end();
    }

}
