package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.Font;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.app.JpizeApplication;
import jpize.sdl.input.Key;

import java.util.StringJoiner;

public class KeyboardTest extends JpizeApplication{

    TextureBatch batch;
    Font font;

    public void init(){
        Gl.clearColor(0.4, 0.5, 0.7);
        batch = new TextureBatch();

        font = FontLoader.loadFnt("fonts/font.fnt");
        font.setScale(0.5F);
        font.options.wrapThreesholdWidth = Jpize.getWidth();
        font.options.invLineWrap = true;
    }

    public void update(){
        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreenDesktop();
    }

    public void render(){
        Gl.clearColorBuffer();
        batch.begin();

        StringJoiner pressedKeys = new StringJoiner(", ");
        for(Key key: Key.values())
            if(key.isPressed())
                pressedKeys.add(key.toString());

        font.drawText(batch, "Pressed keys: " + pressedKeys, 0, Jpize.getHeight());
        batch.end();
    }

}
