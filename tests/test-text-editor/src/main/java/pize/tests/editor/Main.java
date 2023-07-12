package pize.tests.editor;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.gl.Gl;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.util.io.TextProcessor;

import java.util.List;
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
        
        // Iterate lines
        final StringJoiner lineNumbersJoiner = new StringJoiner("\n");
        final List<String> lines = text.getLines();
        for(int i = 0; i < lines.size(); i++){
            // Add line number
            lineNumbersJoiner.add(String.valueOf(i + 1));
            
            // Draw line background
            final float lineWidth = font.getLineWidth(lines.get(i));
            final float advance = font.getLineAdvanceScaled();
            batch.drawQuad(0.1, 0.15, 0.2, 1,  50, 10 + (lines.size() - 1 - i) * advance,  lineWidth, advance);
            batch.drawQuad(0.3, 0.45, 0.5, 1,  0 , 10 + (lines.size() - 1 - i) * advance,  50, advance);
        }
        // Draw line numbers
        font.drawText(batch, lineNumbersJoiner.toString(), 5, 10);
        
        // Draw text
        font.drawText(batch, text.toString(), 50, 10);
        
        // Draw cursor
        if(System.currentTimeMillis() / 500 % 2 == 0 && text.isActive()){
            final String currentLine = text.getCurrentLine();
            final float cursorY = (text.getCursorY() + 1) * font.getLineAdvanceScaled() - font.getLineAdvanceScaled() * text.getLines().size();
            final float cursorX = font.getLineWidth(currentLine.substring(0, text.getCursorX()));
            batch.drawQuad(1, 1, 1, 1,  50 + cursorX, 10 + cursorY, 2, font.getLineAdvanceScaled());
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
