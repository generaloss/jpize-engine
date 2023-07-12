package pize.graphics.font;

import pize.app.Disposable;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;
import pize.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class BitmapFont implements Disposable{

    public static final float ITALIC_ANGLE = 15;

    private final Map<Integer, Glyph> glyphs = new HashMap<>();
    private final Map<Integer, Texture> pages = new HashMap<>();
    private int lineHeight;
    private float scale, rotation, lineGaps;
    private boolean italic;

    protected BitmapFont(){
        setScale(1);
    }


    public float getScale(){
        return scale;
    }

    public void setScale(float scale){
        this.scale = scale;
    }


    public float getRotation(){
        return rotation;
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
    }


    public boolean isItalic(){
        return italic;
    }

    public void setItalic(boolean italic){
        this.italic = italic;
    }
    
    
    public float getLineGaps(){
        return lineGaps;
    }
    
    public void setLineGaps(float lineGaps){
        this.lineGaps = lineGaps;
    }


    public Glyph getGlyph(int code){
        return glyphs.get(code);
    }

    public void addGlyph(Glyph glyph){
        glyphs.put(glyph.id, glyph);
    }


    public Texture getPage(int id){
        return pages.get(id);
    }

    public void addPage(int id, Texture page){
        pages.put(id, page);
    }


    public float getLineHeight(){
        return lineHeight;
    }
    
    public float getLineHeightScaled(){
        return lineHeight * scale;
    }
    
    public float getLineAdvance(){
        return lineHeight + lineGaps;
    }
    
    public float getLineAdvanceScaled(){
        return getLineAdvance() * scale;
    }

    public void setLineHeight(int lineHeight){
        this.lineHeight = lineHeight;
    }
    
    
    public Vec2f getBounds(String text){
        return this.getBounds(text, -1);
    }
    
    public Vec2f getBounds(String text, double width){
        final float lineAdvance = getLineAdvance();
        
        float maxAdvanceX = 0;
        float advanceX = 0;
        float advanceY = lineAdvance;
        
        for(int i = 0; i < text.length(); i++){
            final int code = Character.codePointAt(text, i);
            
            final Glyph glyph = glyphs.get(code);
            if(glyph == null)
                continue;
            
            if(code == 10){
                maxAdvanceX = Math.max(maxAdvanceX, advanceX);
                advanceX = 0;
                advanceY += lineAdvance;
                continue;
            }
            
            if(width > 0 && (advanceX + glyph.advanceX ) * scale > width){
                maxAdvanceX = Math.max(maxAdvanceX, advanceX);
                advanceX = 0;
                advanceY += lineAdvance;
            }

            advanceX += glyph.advanceX;
        }
        
        return new Vec2f(Math.max(advanceX, maxAdvanceX), advanceY).mul(scale);
    }

    public float getLineWidth(String line){
        float advanceX = 0;
        
        for(int i = 0; i < line.length(); i++){
            final int code = Character.codePointAt(line, i);
            if(code == 10)
                continue;
            
            final Glyph glyph = glyphs.get(code);
            if(glyph == null)
                continue;
            
            advanceX += glyph.advanceX;
        }
        
        return advanceX * scale;
    }
    
    public void drawText(TextureBatch batch, String text, float x, float y){
        this.drawText(batch, text, x, y, -1);
    }
    
    public void drawText(TextureBatch batch, String text, float x, float y, double width){
        if(text == null)
            return;
        
        final float lineGaps = lineHeight;
        
        float advanceX = 0;
        float advanceY = lineHeight * StringUtils.count(text, "\n");

        batch.setTransformOrigin(0, 0);
        batch.rotate(rotation);
        batch.shear(italic ? ITALIC_ANGLE : 0, 0);
        
        // Calculate centering offset
        final Vec2f bounds = getBounds(text, width);
        final double angle = rotation * Maths.toRad + Math.atan(bounds.y / bounds.x);
        final float boundsCenter = Mathc.hypot(bounds.x / 2, bounds.y / 2);
        final float centeringOffsetX = boundsCenter * Mathc.cos(angle) - bounds.x / 2;
        final float centeringOffsetY = boundsCenter * Mathc.sin(angle) - bounds.y / 2;

        // Rotation
        final float cos = Mathc.cos(rotation * Maths.toRad);
        final float sin = Mathc.sin(rotation * Maths.toRad);

        for(int i = 0; i < text.length(); i++){
            final int code = Character.codePointAt(text, i);
            
            if(code == 10){
                advanceY -= lineGaps;
                advanceX = 0;
                continue;
            }
            
            final Glyph glyph = glyphs.get(code);
            if(glyph == null)
                continue;
            
            if(width > 0 && (advanceX + glyph.advanceX) * scale > width){
                advanceY -= lineGaps;
                advanceX = 0;
            }
            
            final float xOffset = (advanceX + glyph.offsetX) * scale;
            final float yOffset = (advanceY + glyph.offsetY) * scale;
            
            final float renderX = x + xOffset * cos - yOffset * sin - centeringOffsetX;
            final float renderY = y + yOffset * cos + xOffset * sin - centeringOffsetY;

            glyph.render(batch, renderX, renderY);

            advanceX += glyph.advanceX;
        }

        batch.rotate(0);
        batch.shear(0, 0);
    }


    @Override
    public void dispose(){
        for(Texture page: pages.values())
            page.dispose();
    }

}