package pize.tests.voxelgame.base.text;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.font.Glyph;
import pize.graphics.util.batch.TextureBatch;
import pize.graphics.util.color.Color;
import pize.math.Maths;

import java.util.List;

public class TextComponentBatch implements Disposable{
    
    private final TextureBatch batch;
    private final BitmapFont font;
    
    public TextComponentBatch(){
        this.batch = new TextureBatch(1024);
        this.font = FontLoader.loadFnt("font/default.fnt");
        font.setScale(3);
    }
    
    public void drawText(List<ComponentText> components, float x, float y, float width, float alpha){
        // Подготовим batch и font
        batch.begin();
        font.setScale(Maths.round(Pize.getHeight() / 300F));
        
        // Позиция следующего символа
        float advanceX = 0;
        float advanceY = 0;
        
        // Пройдемся по каждому компоненту дерева, разложенному в плоский список
        for(ComponentText component: components){
            
            // Стиль компонента
            final TextStyle style = component.getStyle();
            
            if(style.isItalic())
                batch.shear(15, 0);
            else
                batch.shear(0, 0);
            
            // Цвет компонента
            final Color color = component.getColor().clone();
            color.setA(color.a() * alpha);
            
            // Текст
            final String text = component.getText();
            
            // Рендерим каждый символ
            for(int i = 0; i < text.length(); i++){
                final char code = text.charAt(i);
                
                // Перенос на новую строку соответствующим символом
                if(code == 10){
                    advanceX = 0;
                    advanceY -= font.getLineHeight();
                    continue;
                }
                
                // Глиф
                final Glyph glyph = font.getGlyph(code);
                if(glyph == null)
                    continue;
                
                // Перенос на новую строку если текст не вмещается в заданную ширину
                if(width > 0 && (advanceX + glyph.advanceX) * font.getScale() >= width){
                    advanceX = 0;
                    advanceY -= font.getLineHeight();
                }
                
                // Координаты глифа
                final float glyphX = x + (advanceX + glyph.offsetX) * font.getScale();
                final float glyphY = y + (advanceY + glyph.offsetY) * font.getScale();
                
                // Рендерим тень
                batch.setColor(color.clone().mul(0.25));
                glyph.render(batch, glyphX + font.getScale(), glyphY - font.getScale());
                // Рендерим основной символ
                batch.setColor(color);
                glyph.render(batch, glyphX, glyphY);
                
                advanceX += glyph.advanceX;
            }
        }
        
        // Закончим отрисовку
        batch.end();
    }
    
    
    public TextureBatch getBatch(){
        return batch;
    }
    
    public BitmapFont getFont(){
        return font;
    }
    
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }

}
