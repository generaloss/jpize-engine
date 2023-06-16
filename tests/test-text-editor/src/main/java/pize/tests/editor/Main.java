package pize.tests.editor;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.gl.Gl;
import pize.graphics.util.TextureUtils;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.math.vecmath.tuple.Tuple2f;
import pize.util.io.TextProcessor;

import java.util.StringJoiner;

public class Main extends AppAdapter{
    
    public static void main(String[] args){
        Pize.create("Editor", 1280, 720);
        Pize.run(new Main());
    }
    
    
    private TextureBatch batch;
    private BitmapFont font;
    private TextProcessor text;
    
    @Override
    public void init(){
        batch = new TextureBatch();
        font = FontLoader.getDefault();
        text = new TextProcessor(true);
    }
    
    @Override
    public void render(){
        if(Key.LEFT_CONTROL.isPressed() && Key.Y.isDown())
            text.removeLine();
        
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.2, 1);
        batch.begin();
        
        // Draw background
        Tuple2f bounds = font.getBounds(text.toString());
        batch.setColor(0.1, 0.15, 0.2, 1);
        batch.draw(TextureUtils.quadTexture(), 50, 10, bounds.x, bounds.y);
        batch.setColor(0.3, 0.45, 0.5, 1);
        batch.draw(TextureUtils.quadTexture(), 0, 10, 50, bounds.y);
        batch.resetColor();
        
        // Draw line numbers
        final StringJoiner lineNumbersJoiner = new StringJoiner("\n");
        for(int i = 0; i < text.getLines().size(); i++)
            lineNumbersJoiner.add(String.valueOf(i + 1));
        font.drawText(batch, lineNumbersJoiner.toString(), 5, 10);
        
        // Draw text
        font.drawText(batch, text.toString(), 50, 10);
        
        // Draw cursor
        if(System.currentTimeMillis() / 500 % 2 == 0 && text.isActive()){
            final String currentLine = text.getCurrentLine();
            final float cursorY = font.getBounds(text.toString()).y - (text.getCursorY() + 1) * font.getScaledLineHeight();
            final float cursorX = font.getLineWidth(currentLine.substring(0, text.getCursorX()));
            batch.draw(TextureUtils.quadTexture(), 50 + cursorX, 10 + cursorY, 2, font.getScaledLineHeight());
        }
        
        batch.end();
    }
    
    @Override
    public void dispose(){
        text.dispose();
        batch.dispose();
        font.dispose();
    }
    
}
