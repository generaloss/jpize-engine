package pize.graphics.font;

import pize.graphics.texture.Region;
import pize.graphics.util.batch.Batch;

public class Glyph{

    public BitmapFont fontOf;

    public final int id;

    public final float offsetX;
    public final float offsetY;
    public final float width;
    public final float height;

    public final Region region;
    public final float advanceX;
    public final int page;


    public Glyph(BitmapFont fontOf, int id, float offsetX, float offsetY, float width, float height, Region region, float advanceX, int page){
        this.fontOf = fontOf;

        this.id = id;

        this.region = region;

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        this.advanceX = advanceX;
        this.page = page;
    }


    public void render(Batch batch, float x, float y){
        final float scale = fontOf.getScale();

        batch.draw(
            fontOf.getPage(page),

            x,
            y,
            width * scale,
            height * scale,

            region
        );
    }

}