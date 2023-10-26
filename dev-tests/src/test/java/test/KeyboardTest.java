package test;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.glfw.key.Key;
import jpize.gl.Gl;

import java.util.StringJoiner;

public class KeyboardTest extends JpizeApplication{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        Gl.clearColor(0.4, 0.5, 0.7);
        batch = new TextureBatch();

        font = FontLoader.loadFnt("fonts/font.fnt");
        font.setScale(0.5F);
        font.getOptions().textAreaWidth = Jpize.getWidth();
        font.getOptions().invLineWrap = true;

        Jpize.keyboard().setStickyKeys(true);
    }

    public void update(){
        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreen();
    }

    public void render(){
        Gl.clearColorBuffer();
        batch.begin();

        StringJoiner pressedKeys = new StringJoiner(", ");
        for(Key key: Key.values())
            if(key.isPressed())
                pressedKeys.add(key.getName());

        font.drawText(batch, "Pressed keys: " + pressedKeys, 0, Jpize.getHeight());

        batch.end();
    }

}
