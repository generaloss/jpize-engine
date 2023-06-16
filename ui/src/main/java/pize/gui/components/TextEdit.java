package pize.gui.components;

import pize.app.Disposable;
import pize.graphics.font.BitmapFont;
import pize.graphics.util.batch.TextureBatch;
import pize.util.io.TextProcessor;

public class TextEdit extends TextView implements Disposable{
    
    private final TextProcessor textProcessor;
    
    public TextEdit(BitmapFont font, boolean newLineOnEnter){
        super("", font);
        
        textProcessor = new TextProcessor(newLineOnEnter);
    }
    
    
    @Override
    public void render(TextureBatch batch, float x, float y, float width, float height){
        super.setText(textProcessor.toString());
        super.render(batch, x, y, width, height);
    }
    
    public void dispose(){
        textProcessor.dispose();
    }
    
}
