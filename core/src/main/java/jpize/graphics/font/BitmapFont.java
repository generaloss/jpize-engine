package jpize.graphics.font;

import jpize.graphics.font.glyph.GlyphIterator;
import jpize.graphics.font.glyph.GlyphMap;
import jpize.graphics.font.glyph.GlyphPages;
import jpize.graphics.font.glyph.GlyphSprite;
import jpize.graphics.util.batch.TextureBatch;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2f;
import jpize.util.Disposable;

public class BitmapFont implements Disposable{

    private final FontInfo info;
    private final GlyphPages pages;
    private final GlyphMap glyphs;
    private final FontOptions options;

    protected BitmapFont(FontInfo info, GlyphPages pages, GlyphMap glyphs){
        this.info = info;
        this.pages = pages;
        this.glyphs = glyphs;
        this.options = new FontOptions(this);
    }

    public FontInfo getInfo(){
        return info;
    }

    public GlyphMap getGlyphs(){
        return glyphs;
    }

    public GlyphPages getPages(){
        return pages;
    }

    public FontOptions getOptions(){
        return options;
    }


    public float getLineHeight(){
        return info.getHeight();
    }

    public float getDescentScaled(){
        return info.getDescent() * options.scale;
    }


    public float getScale(){
        return options.scale;
    }

    public void setScale(float scale){
        options.scale = scale;
    }

    
    public Vec2f getBounds(String text){
        float width = 0;
        float height = 0;

        for(GlyphSprite glyph: iterableText(text)){
            final float glyphX = glyph.getX() + ((char) glyph.getCode() == ' ' ? glyph.getAdvanceX() : glyph.getWidth());
            final float glyphY = Maths.abs(glyph.getY() + info.getDescent() + glyph.getHeight()) - info.getDescent();

            width = Math.max(width, glyphX);
            height = Math.max(height, glyphY);
        }

        return new Vec2f(width, height);
    }

    public float getTextWidth(String text){
        float width = 0;
        for(GlyphSprite glyph: iterableText(text)){
            final float glyphX = glyph.getX() + ((char) glyph.getCode() == ' ' ? glyph.getAdvanceX() : glyph.getWidth());
            width = Math.max(width, glyphX);
        }
        return width;
    }

    public float getTextHeight(String text){
        float height = 0;
        for(GlyphSprite glyph: iterableText(text)){
            final float glyphY = Maths.abs(glyph.getY() + info.getDescent() + glyph.getHeight()) - info.getDescent();
            height = Math.max(height, glyphY);
        }
        return height;
    }


    public void drawText(TextureBatch batch, String text, float x, float y){
        if(text == null)
            return;

        batch.setTransformOrigin(0, 0);
        batch.rotate(options.rotation);
        batch.shear(options.getItalicAngle(), 0);

        final Vec2f centerPos = getBounds(text).mul(options.rotateOrigin);
        centerPos.y *= options.getLineWrapSign();

        final float descent = getDescentScaled();

        for(GlyphSprite sprite: iterableText(text)){
            if((char) sprite.getCode() == ' ')
                continue;

            final Vec2f renderPos = new Vec2f(sprite.getX(), sprite.getY());
            renderPos.y -= descent;
            renderPos.sub(centerPos).rotDeg(options.rotation).add(centerPos).add(x, y);
            renderPos.y += descent;
            sprite.render(batch, renderPos.x, renderPos.y);
        }

        batch.rotate(0);
        batch.shear(0, 0);
    }


    public Iterable<GlyphSprite> iterableText(String text){
        return () -> new GlyphIterator(glyphs, options, text, 1, 1);
    }


    @Override
    public void dispose(){
        pages.dispose();
    }

}