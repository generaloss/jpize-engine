package pize.devtests;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.lib.gl.Gl;

public class FontTest extends AppAdapter{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        Pize.window().setSize(1280, 720);

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
        font.drawText(batch, text, 0, Pize.getHeight(), Pize.getWidth(), true);
        batch.end();
    }

}
