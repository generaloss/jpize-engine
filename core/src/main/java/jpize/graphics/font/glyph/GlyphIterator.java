package jpize.graphics.font.glyph;

import jpize.graphics.font.FontOptions;
import jpize.util.math.vecmath.vector.Vec2f;

import java.util.Iterator;

public class GlyphIterator implements Iterator<GlyphSprite>{

    private final GlyphMap glyphs;
    private final FontOptions options;
    private final CharSequence text;
    private final int size;
    private final Vec2f advanceFactor;

    private int cursor;
    private float advanceX;
    private float advanceY;

    public GlyphIterator(GlyphMap glyphs, FontOptions options, CharSequence text, float advanceFactorX, float advanceFactorY){
        this.glyphs = glyphs;
        this.options = options;
        this.text = textWithoutNullGlyphs(text);
        this.size = this.text.length();

        this.advanceFactor = new Vec2f(advanceFactorX, advanceFactorY);

        this.cursor = 0;
        this.advanceX = 0;
        this.advanceY = 0;

        if(options.invLineWrap)
            this.advanceY -= options.getAdvance();
    }

    private CharSequence textWithoutNullGlyphs(CharSequence text){
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < text.length(); i++){
            final int code = Character.codePointAt(text, i);
            if(code == 10 || glyphs.has(code))
                builder.append((char) code);
        }

        return builder.toString();
    }


    @Override
    public boolean hasNext(){
        return cursor < size;
    }

    @Override
    public GlyphSprite next(){
        final Glyph glyph = findNextGlyph();

        // Scale
        final float scale = options.scale;

        // Last '\n' for correct bounds
        if(glyph == null){
            final float y = advanceY * scale;
            return new GlyphSprite(y, options.getAdvanceScaled());
        }

        // Wrap line (area width)
        final double textAreaWidth = options.textAreaWidth;
        if(textAreaWidth >= 0 && (advanceX + glyph.advanceX * advanceFactor.x) * scale > textAreaWidth){
            advanceX = 0;
            advanceY += options.getLineWrapSign() * options.getAdvance() * advanceFactor.y;
        }

        // Calc position
        final float x = (advanceX + glyph.offset.x) * scale;
        final float y = (advanceY + glyph.offset.y) * scale;

        // Advance advance :)
        advanceX += glyph.advanceX * advanceFactor.x;
        cursor++;

        // Return
        return new GlyphSprite(glyph, x, y, scale);
    }

    private Glyph findNextGlyph(){
        int code = -1;

        while(hasNext()){
            code = Character.codePointAt(text, cursor);
            if(code != 10)
                break;

            // Wrap line
            advanceX = 0;
            advanceY += options.getLineWrapSign() * options.getAdvance() * advanceFactor.y;

            cursor++;
        }

        return glyphs.get(code);
    }

}
