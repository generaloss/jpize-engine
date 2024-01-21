package test;

import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.graphics.font.Font;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.sdl.input.Key;
import jpize.util.math.vecmath.vector.Vec2f;

public class GlyphIteratorTest extends JpizeApplication{

    String STRING = ".,padiI want pizza 2285422854 end.";

    TextureBatch batch;
    Font font;
    String substring;
    int symbols;

    public GlyphIteratorTest(){
        this.batch = new TextureBatch();
        this.font = FontLoader.getDefault();
        // this.font.options.invLineWrap = true;
        this.substring = "";
    }

    public void update(){
        if(Key.A.isDown() && symbols++ < STRING.length())
            substring = STRING.substring(0, symbols);
    }

    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.3, 0.3, 0.3);
        batch.begin();

        Vec2f bounds = font.getBounds(substring);
        batch.drawRect(0.8, 0.2, 0.1, 0.9, 100, 100, bounds.x, 2);
        batch.drawRect(0.1, 0.7, 0.3, 0.3, 100, 100, bounds.x, bounds.y);

        font.drawText(batch, substring, 100, 100);

        batch.end();
    }

}
