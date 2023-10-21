package jpize.tests.mcose.client.renderer.text;

import jpize.Jpize;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.font.glyph.Glyph;
import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.graphics.util.color.IColor;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2f;
import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.ComponentText;
import jpize.tests.mcose.main.text.TextStyle;
import jpize.util.Disposable;

import java.util.List;

public class TextComponentBatch implements Disposable{
    
    private final TextureBatch batch;
    private final BitmapFont font;
    private final Color background;
    
    public TextComponentBatch(){
        this.batch = new TextureBatch(1024);
        this.background = new Color();

        this.font = FontLoader.loadFnt("font/minecraft/default.fnt");
        this.font.setScale(3);
        this.font.getOptions().lineGaps = 1;
    }
    
    
    public void updateScale(){
        font.setScale(Maths.round(Jpize.getHeight() / 330F));
    }
    
    
    public void drawComponents(List<ComponentText> components, float x, float y, float width, float alpha){
        // Подготовим batch и font
        batch.begin();

        final float scale = font.getScale();
        
        // Позиция следующего символа
        final float lineAdvance = font.getOptions().getAdvance();
        
        float advanceX = 0;
        float advanceY = 0;
        
        // Фон
        if(background.a() != 0){
            final StringBuilder text = new StringBuilder();
            for(ComponentText component: components)
                text.append(component.toString());
            
            final Vec2f bounds = font.getBounds(text.toString());
            batch.drawQuad(background, x, y, bounds.x, bounds.y);
        }
        
        // Пройдемся по каждому компоненту дерева, разложенному в плоский список
        for(ComponentText component: components){
            
            // Стиль компонента
            final TextStyle style = component.getStyle();
            batch.shear(style.isItalic() ? 15 : 0, 0);
            
            // Цвет компонента
            final Color color = component.getColor().copy();
            color.setA(color.a() * alpha);
            
            // Текст
            final String text = component.getText();
            
            // Рендерим каждый символ
            for(int i = 0; i < text.length(); i++){
                final char code = text.charAt(i);
                
                // Перенос на новую строку соответствующим символом
                if(code == 10){
                    advanceX = 0;
                    advanceY -= lineAdvance;
                    continue;
                }
                
                // Глиф
                final Glyph glyph = font.getGlyphs().get(code);
                if(glyph == null)
                    continue;
                
                // Перенос на новую строку если текст не вмещается в заданную ширину
                if(width > 0 && (advanceX + glyph.advanceX) * scale >= width){
                    advanceX = 0;
                    advanceY -= lineAdvance;
                }
                
                // Координаты глифа
                final float glyphX = x + (advanceX + glyph.offset.x) * scale;
                final float glyphY = y + (advanceY + glyph.offset.y) * scale;
                
                // Рендерим тень
                batch.setColor(color.copy().mul3(0.25));
                glyph.render(batch, glyphX + scale, glyphY - scale, scale);
                // Рендерим основной символ
                batch.setColor(color);
                glyph.render(batch, glyphX, glyphY, scale);
                
                advanceX += glyph.advanceX;
            }
        }
        
        // Закончим отрисовку
        batch.shear(0, 0);
        batch.resetColor();
        batch.end();
    }
    
    
    public void drawComponents(List<ComponentText> components, float x, float y, float alpha){
        drawComponents(components, x, y, -1, alpha);
    }
    
    public void drawComponents(List<ComponentText> components, float x, float y){
        drawComponents(components, x, y, 1F);
    }
    
    
    public void drawComponent(Component component, float x, float y, float width, float alpha){
        drawComponents(component.toFlatList(), x, y, width, alpha);
    }
    
    public void drawComponent(Component component, float x, float y, float alpha){
        drawComponents(component.toFlatList(), x, y, -1, alpha);
    }
    
    public void drawComponent(Component component, float x, float y){
        drawComponents(component.toFlatList(), x, y, 1F);
    }
    
    
    public TextureBatch getBatch(){
        return batch;
    }
    
    public BitmapFont getFont(){
        return font;
    }
    
    
    public void setBackgroundColor(double r, double g, double b, double a){
        background.set(r, g, b, a);
    }
    
    public void setBackgroundColor(double r, double g, double b){
        background.set3(r, g, b);
    }
    
    public void setBackgroundColor(IColor color){
        background.set(color);
    }
    
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }

}
