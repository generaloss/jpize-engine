package jpize.devtests;

import jpize.Jpize;
import jpize.glfw.key.Key;
import jpize.io.context.JpizeApplication;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gl.Gl;
import jpize.util.io.TextProcessor;

public class TextTest extends JpizeApplication{

    TextureBatch batch;
    BitmapFont font;
    TextProcessor processor;

    public void init(){
        Jpize.window().setSize(1280, 720);
        batch = new TextureBatch();

        font = FontLoader.getDefault();
        font.setScale(1);

        processor = new TextProcessor();
        processor.insertText("1) I want pizza\n");
        processor.insertText("2) I want pizza\n");
        processor.insertText("3) I want pizza\n");
    }

    public float rotation = 0;

    public void update(){
        if(Key.ESCAPE.isDown())
            Jpize.exit();

        rotation++;
    }

    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);

        batch.begin();

        // Render text 1
        font.setRotation(rotation);
        batch.setColor(0.5, 0.2, 0.3);
        font.drawText(batch, processor.getString(), 500, 500, -1, true);

        final String text2 =
                """
                public static void main
                i want pizza
                third line
                что-то на русском
                """;

        // Render text2
        batch.resetColor();
        font.setRotation(0);
        font.drawText(batch, text2, 0, Jpize.getHeight(), Jpize.getWidth(), true);

        batch.end();
    }

}
