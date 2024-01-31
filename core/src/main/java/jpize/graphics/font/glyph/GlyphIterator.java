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
    private int lineY;
    private float cursorX;
    private float cursorY;

    public GlyphIterator(GlyphMap glyphs, FontOptions options, CharSequence text, float advanceFactorX, float advanceFactorY){
        this.glyphs = glyphs;
        this.options = options;
        this.text = textWithoutNullGlyphs(text);
        this.size = this.text.length();

        this.advanceFactor = new Vec2f(advanceFactorX, advanceFactorY);

        this.cursorY = -(options.invLineWrap ? options.getAdvance() : 0);
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
        if(glyph == null)
            return new GlyphSprite(cursorY, options.getAdvance(), scale, lineY);

        final float cursorXAdvance = glyph.advanceX * advanceFactor.x;

        // Wrap line (area width)
        final double maxWidth = options.wrapThreesholdWidth / scale;
        if(maxWidth >= 0 && cursorX + cursorXAdvance > maxWidth){
            cursorX = 0;
            cursorY += options.getLineWrapSign() * options.getAdvance() * advanceFactor.y;
            lineY++;
        }

        // Create sprite
        final GlyphSprite sprite = new GlyphSprite(glyph, cursorX, cursorY, scale, lineY);

        // Advance cursor :|
        cursorX += cursorXAdvance;
        cursor++;

        return sprite;
    }

    private Glyph findNextGlyph(){
        int code = -1;

        while(hasNext()){
            code = text.charAt(cursor);
            if(code != '\n')
                break;

            // Wrap line
            cursorX = 0;
            cursorY += options.getLineWrapSign() * options.getAdvance() * advanceFactor.y;
            lineY++;

            cursor++;
        }

        return glyphs.get(code);
    }

    private CharSequence textWithoutNullGlyphs(CharSequence text){
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < text.length(); i++){
            final int code = Character.codePointAt(text, i);
            if(code == '\n' || glyphs.has(code))
                builder.append((char) code);
        }

        return builder.toString();
    }

}
