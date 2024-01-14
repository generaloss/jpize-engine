package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.app.JpizeApplication;

public class BatchPerformanceTest extends JpizeApplication{

    TextureBatch batch = new TextureBatch();
    Texture texture = new Texture("textures/texture17.png");
    BitmapFont font = FontLoader.getDefault();

    public void render(){
        Gl.clearColorBuffer();

        batch.begin();
        for(int i = 0; i < Jpize.getWidth() / 10; i++)
            for(int j = 0; j < Jpize.getHeight() / 10; j++)
                batch.draw(texture, i * 10, j * 10, 10, 10);
        batch.end();

        font.drawText("fps: " + Jpize.getFPS(), 10, 10);
    }

}
