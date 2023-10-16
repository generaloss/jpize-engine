package jpize.graphics.font;

import jpize.graphics.font.glyph.GlyphIterator;
import jpize.graphics.font.glyph.GlyphMap;
import jpize.graphics.font.glyph.GlyphPages;
import jpize.graphics.font.glyph.GlyphSprite;
import jpize.graphics.util.batch.TextureBatch;
import jpize.math.vecmath.vector.Vec2f;
import jpize.util.Disposable;

public class BitmapFont implements Disposable{

    public static final float ITALIC_ANGLE = 15;

    private final GlyphPages pages;
    private final GlyphMap glyphs;
    private int lineHeight;
    private float scale, rotation, lineGaps;
    private boolean italic;

    protected BitmapFont(){
        this.pages = new GlyphPages();
        this.glyphs = new GlyphMap();
        this.scale = 1;
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


    public GlyphMap getGlyphs(){
        return glyphs;
    }

    public GlyphPages getPages(){
        return pages;
    }


    public float getLineHeight(){
        return lineHeight;
    }

    public float getLineHeightScaled(){
        return lineHeight * scale;
    }

    public void setLineHeight(int lineHeight){
        this.lineHeight = lineHeight;
    }


    public float getLineAdvance(){
        return lineHeight + lineGaps;
    }

    public float getLineAdvanceScaled(){
        return getLineAdvance() * scale;
    }

    
    public Vec2f getBounds(String text, double textAreaWidth){
        float width = 0;
        float height = 0;

        for(GlyphSprite glyph: glyphIterable(text, 0, 0, textAreaWidth, false)){
            width = Math.max(width, glyph.getX() + glyph.getWidth());
            height = Math.max(height, glyph.getY() + glyph.getHeight() + lineGaps * scale);
        }

        return new Vec2f(width, height);
    }

    public Vec2f getBounds(String text){
        return this.getBounds(text, -1);
    }


    public float getLineWidth(String line){
        float width = 0;
        for(GlyphSprite glyph: glyphIterable(line))
            width = Math.max(width, glyph.getX() + glyph.getWidth());

        return width;
    }


    public float getTextHeight(String text, double textAreaWidth){
        float height = 0;
        for(GlyphSprite glyph: glyphIterable(text, 0, 0, textAreaWidth, false))
            height = Math.max(height, glyph.getY() + glyph.getHeight());

        return height;
    }

    public Vec2f getTextHeight(String text){
        return this.getBounds(text, -1);
    }


    public void drawText(TextureBatch batch, String text, float x, float y, double textAreaWidth, boolean invWrapY){
        if(text == null)
            return;

        batch.setTransformOrigin(0, 0);
        batch.rotate(rotation);
        batch.shear(italic ? ITALIC_ANGLE : 0, 0);

        final Vec2f centerPos = getBounds(text, textAreaWidth).mul(0.5, 0.5);
        if(invWrapY)
            centerPos.y *= -1;

        for(GlyphSprite sprite: glyphIterable(text, 0, 0, textAreaWidth, invWrapY)){
            final Vec2f renderPos = new Vec2f(sprite.getX(), sprite.getY());
            renderPos.sub(centerPos).rotDeg(rotation).add(centerPos).add(x, y);
            sprite.render(batch, renderPos.x, renderPos.y);
        }

        batch.rotate(0);
        batch.shear(0, 0);
    }

    public void drawText(TextureBatch batch, String text, float x, float y, double width){
        this.drawText(batch, text, x, y, width, false);
    }

    public void drawText(TextureBatch batch, String text, float x, float y){
        this.drawText(batch, text, x, y, -1);
    }


    public Iterable<GlyphSprite> glyphIterable(String text, float x, float y, double textAreaWidth, boolean invLineWrap){
        return () -> new GlyphIterator(
            glyphs,
            text,
            x, y,
            getLineAdvance(),
            scale,
            1, 1,
            textAreaWidth,
            invLineWrap
        );
    }

    public Iterable<GlyphSprite> glyphIterable(String text, float x, float y){
        return glyphIterable(text, x, y, -1, false);
    }

    public Iterable<GlyphSprite> glyphIterable(String text){
        return glyphIterable(text, 0, 0);
    }


    @Override
    public void dispose(){
        pages.dispose();
    }

}