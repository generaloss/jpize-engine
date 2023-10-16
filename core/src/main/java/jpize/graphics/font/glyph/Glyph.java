package jpize.graphics.font.glyph;

import jpize.graphics.texture.Region;
import jpize.graphics.util.batch.TextureBatch;
import jpize.math.vecmath.vector.Vec2f;

public class Glyph{

    public final int code;

    public final Vec2f offset;
    public final float width;
    public final float height;

    public final Region region;
    public final float advanceX;

    public GlyphPages pages;
    public int page;

    public Glyph(int code, float offsetX, float offsetY, float width, float height, Region region, float advanceX, int page, GlyphPages pages){
        this.code = code;

        this.region = region;

        this.offset = new Vec2f(offsetX, offsetY);
        this.width = width;
        this.height = height;

        this.advanceX = advanceX;
        this.page = page;
        this.pages = pages;
    }


    public void render(TextureBatch batch, float x, float y, float scale){
        batch.draw(pages.get(page), x, y, width * scale, height * scale, region);
    }

}