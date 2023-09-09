package jpize.devtests;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gl.Gl;

public class FontTest extends JpizeApplication{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        Jpize.window().setSize(1280, 720);

        batch = new TextureBatch();
        font = FontLoader.loadFnt("font.fnt");
    }

    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        final String text =
                """
                Public static void main(){
                I want pizza
                Third line and some text
                """;

        batch.begin();
        font.drawText(batch, text, 0, Jpize.getHeight(), Jpize.getWidth(), true);
        batch.end();
    }

}
