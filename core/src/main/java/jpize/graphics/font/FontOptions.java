package jpize.graphics.font;

import jpize.math.vecmath.vector.Vec2f;

public class FontOptions{

    private final BitmapFont font;

    public float scale;
    public float rotation;
    public boolean italic;

    public float lineGaps;
    public float italicAngle;
    public double textAreaWidth;
    public boolean invLineWrap;
    public final Vec2f rotateOrigin;

    public FontOptions(BitmapFont font){
        this.font = font;

        this.scale = 1;
        this.italicAngle = 15;
        this.textAreaWidth = -1;
        this.rotateOrigin = new Vec2f(0.5);
    }

    public BitmapFont getFont(){
        return font;
    }

    public float getAdvance(){
        return font.getLineHeight() + lineGaps;
    }

    public float getAdvanceScaled(){
        return getAdvance() * scale;
    }

    public float getGapsScaled(){
        return lineGaps * scale;
    }

    public float getItalicAngle(){
        if(italic)
            return italicAngle;

        return 0;
    }

    public int getLineWrapSign(){
        return invLineWrap ? -1 : 1;
    }

}
