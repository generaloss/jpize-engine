package pize.devtests;

import pize.Jize;
import pize.io.context.ContextAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.glfw.key.Key;
import pize.gl.Gl;

import java.util.StringJoiner;

public class KeyboardTest extends ContextAdapter{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
        font.setScale(0.5F);

        Jize.keyboard().setStickyKeys(true);
    }

    public void render(){
        if(Key.ESCAPE.isDown())
            Jize.exit();
        if(Key.F11.isDown())
            Jize.window().toggleFullscreen();

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        batch.begin();

        StringJoiner pressedKeys = new StringJoiner(", ");
        for(Key key: Key.values())
            if(key.isPressed())
                pressedKeys.add(key.getName());
        font.drawText(batch, "Pressed keys: " + pressedKeys, 0, Jize.getHeight(), Jize.getWidth(), true);

        batch.end();
    }

}