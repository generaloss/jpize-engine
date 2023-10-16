package jpize.graphics.font.glyph;

import jpize.math.vecmath.vector.Vec2f;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GlyphIterator implements Iterator<GlyphSprite>{

    private final GlyphMap glyphs;
    private final CharSequence text;
    private final int size;
    private final Vec2f begin;
    private final float lineAdvance;
    private final float scale;
    private final Vec2f k;
    private final double textAreaWidth;
    private final int wrapLineSign;

    private int cursor;
    private float advanceX;
    private float advanceY;

    public GlyphIterator(GlyphMap glyphs, CharSequence text, float beginX, float beginY, float lineAdvance, float scale, float kX, float kY, double textAreaWidth, boolean invLineWrap){
        this.glyphs = glyphs;
        this.text = textWithoutNullGlyphs(text);
        this.size = this.text.length();

        this.begin = new Vec2f(beginX, beginY);
        this.lineAdvance = lineAdvance;
        this.scale = scale;
        this.k = new Vec2f(kX, kY);
        this.textAreaWidth = textAreaWidth;
        this.wrapLineSign = (invLineWrap ? -1 : 1);

        this.cursor = 0;
        this.advanceX = 0;
        this.advanceY = (invLineWrap ? -lineAdvance : 0);
    }

    private CharSequence textWithoutNullGlyphs(CharSequence text){
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < text.length(); i++){
            final int code = Character.codePointAt(text, i);
            if(code == 10 || glyphs.has(code))
                builder.append((char) code);
        }

        return builder.toString().trim();
    }


    @Override
    public boolean hasNext(){
        return cursor < size;
    }

    @Override
    public GlyphSprite next(){
        final Glyph glyph = findNextGlyph();

        // Wrap line (area width)
        if(textAreaWidth >= 0 && (advanceX + glyph.advanceX * k.x) * scale > textAreaWidth){
            advanceX = 0;
            advanceY += wrapLineSign * lineAdvance * k.y;
        }

        // Calc position
        final float offsetX = (advanceX + glyph.offset.x) * scale;
        final float offsetY = (advanceY + glyph.offset.y) * scale;

        final float renderX = begin.x + offsetX;
        final float renderY = begin.y + offsetY;

        // Advance advance :)
        advanceX += glyph.advanceX * k.x;
        cursor++;

        // Return
        return new GlyphSprite(glyph, renderX, renderY, scale);
    }

    private Glyph findNextGlyph(){
        if(!hasNext())
            throw new NoSuchElementException();

        int code = -1;

        while(hasNext()){
            code = Character.codePointAt(text, cursor);
            if(code != 10)
                break;

            // Wrap line
            advanceX = 0;
            advanceY += wrapLineSign * lineAdvance * k.y;
            cursor++;
        }

        return glyphs.get(code);
    }

}
