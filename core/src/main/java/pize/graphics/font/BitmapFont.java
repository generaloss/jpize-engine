package pize.graphics.font;

import pize.activity.Disposable;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.tuple.Tuple2f;
import pize.math.vecmath.vector.Vec2f;

import java.util.HashMap;
import java.util.Map;

public class BitmapFont implements Disposable{

    public static final float ITALIC_ANGLE = 15;

    private final Map<Integer, Glyph> glyphs = new HashMap<>();
    private final Map<Integer, Texture> pages = new HashMap<>();
    private int lineHeight;
    private float scale, rotation;
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


    public int getLineHeight(){
        return lineHeight;
    }

    public float getScaledLineHeight(){
        return lineHeight * scale;
    }

    public void setLineHeight(int lineHeight){
        this.lineHeight = lineHeight;
    }


    public Tuple2f getBounds(CharSequence text){
        int advanceX = 0;
        int advanceY = lineHeight;

        int maxX = 0;

        for(int i = 0; i < text.length(); i++){
            int code = Character.codePointAt(text, i);

            if(code == 10){
                advanceY += lineHeight;
                advanceX = 0;
                continue;
            }

            Glyph glyph = glyphs.get(code);
            if(glyph == null)
                continue;

            advanceX += glyph.advanceX;

            maxX = Math.max(maxX, advanceX);
        }

        return new Vec2f(maxX, advanceY).mul(scale);
    }

    public Tuple2f getAdvance(String text){
        float advanceX = 0;
        float advanceY = lineHeight * (text.split("\n").length - 1);

        for(int i = 0; i < text.length(); i++){
            int code = Character.codePointAt(text, i);
            if(code == 10){
                advanceY -= lineHeight;
                advanceX = 0;
                continue;
            }

            Glyph glyph = glyphs.get(code);
            if(glyph == null)
                continue;

            advanceX += glyph.advanceX;
            advanceY += glyph.advanceX * Mathc.sin(rotation * Maths.toRad);
        }

        return new Vec2f(advanceX, advanceY).mul(scale);
    }

    public void drawText(TextureBatch batch, String text, float x, float y){
        float advanceX = 0;
        float advanceY = lineHeight * (text.split("\n").length - 1);

        batch.setTransformOrigin(0, 0);
        batch.rotate(rotation);
        batch.shear(italic ? ITALIC_ANGLE : 0, 0);

        // Calculate centering offset
        Tuple2f bounds = getBounds(text);
        double angle = rotation * Maths.toRad + Math.atan(bounds.y / bounds.x);
        float boundsCenter = Mathc.hypot(bounds.x / 2, bounds.y / 2);
        float centeringOffsetX = boundsCenter * Mathc.cos(angle) - bounds.x / 2;
        float centeringOffsetY = boundsCenter * Mathc.sin(angle) - bounds.y / 2;

        // Rotation
        float cos = Mathc.cos(rotation * Maths.toRad);
        float sin = Mathc.sin(rotation * Maths.toRad);

        for(int i = 0; i < text.length(); i++){
            int code = Character.codePointAt(text, i);
            if(code == 10){
                advanceY -= lineHeight;
                advanceX = 0;
                continue;
            }

            Glyph glyph = glyphs.get(code);
            if(glyph == null)
                continue;

            float xOffset = (advanceX + glyph.offsetX) * scale;
            float yOffset = (advanceY + glyph.offsetY) * scale;

            float renderX = x + xOffset * cos - yOffset * sin - centeringOffsetX;
            float renderY = y + yOffset * cos + xOffset * sin - centeringOffsetY;

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