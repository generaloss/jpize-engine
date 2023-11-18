package jpize.graphics.font;

public class FontInfo{

    private final float height;
    private final float ascent;
    private final float descent;

    public FontInfo(float height, float ascent, float descent){
        this.height = height;
        this.ascent = ascent;
        this.descent = descent;
    }

    public float getHeight(){
        return height;
    }

    public float getAscent(){
        return ascent;
    }

    public float getDescent(){
        return descent;
    }

}
