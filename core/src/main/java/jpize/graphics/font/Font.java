package jpize.graphics.font;

import jpize.graphics.font.glyph.GlyphIterator;
import jpize.graphics.font.glyph.GlyphMap;
import jpize.graphics.font.glyph.GlyphPages;
import jpize.graphics.font.glyph.GlyphSprite;
import jpize.graphics.util.batch.TextureBatch;
import jpize.util.math.vecmath.vector.Vec2f;
import jpize.app.Disposable;

public class Font implements Disposable{

    public final FontInfo info;
    public final GlyphPages pages;
    public final GlyphMap glyphs;
    public final FontOptions options;

    protected Font(FontInfo info, GlyphPages pages, GlyphMap glyphs){
        this.info = info;
        this.pages = pages;
        this.glyphs = glyphs;
        this.options = new FontOptions(this);
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

        for(GlyphSprite glyph: iterable(text)){
            final float glyphMaxX = glyph.getX() + ((char) glyph.getCode() == ' ' ? glyph.getAdvanceX() : glyph.getWidth());
            final float glyphMaxY = glyph.getOffsetY() + glyph.getHeight() + glyph.getLineY() * options.getAdvanceScaled();

            width = Math.max(width, glyphMaxX);
            height = Math.max(height, glyphMaxY);
        }

        return new Vec2f(width, height);
    }

    public float getTextWidth(String text){
        float width = 0;
        for(GlyphSprite glyph: iterable(text)){
            final float glyphMaxX = glyph.getX() + ((char) glyph.getCode() == ' ' ? glyph.getAdvanceX() : glyph.getWidth());
            width = Math.max(width, glyphMaxX);
        }
        return width;
    }

    public float getTextHeight(String text){
        float height = 0;
        for(GlyphSprite glyph: iterable(text)){
            final float glyphMaxY = Math.abs(glyph.getY() + info.getDescent() + glyph.getHeight()) - info.getDescent();
            height = Math.max(height, glyphMaxY);
        }
        return height;
    }


    public void drawText(TextureBatch batch, String text, float x, float y){
        TextRenderer.render(this, batch, text, x, y);
    }

    public void drawText(String text, float x, float y){
        TextRenderer.render(this, text, x, y);
    }

    public Iterable<GlyphSprite> iterable(String text){
        return () -> new GlyphIterator(glyphs, options, text, options.advanceFactor.x, options.advanceFactor.y);
    }


    @Override
    public void dispose(){
        pages.dispose();
    }

}