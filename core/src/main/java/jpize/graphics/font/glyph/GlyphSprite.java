package jpize.graphics.font.glyph;

import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;

public class GlyphSprite{

    private final int code;
    private final Region region;
    private final Texture page;

    private final float x;
    private final float y;
    private final float width;
    private final float height;

    private final int lineY;
    private final boolean canRender;
    private final float advanceX;
    private final float offsetY;

    public GlyphSprite(Glyph glyph, float cursorX, float cursorY, float scale, int lineY){
        this.code   = glyph.code;
        this.region = glyph.region;
        this.page   = glyph.pages.get(glyph.pageID);

        this.x      = scale * (glyph.offset.x + cursorX);
        this.y      = scale * (glyph.offset.y + cursorY);
        this.width  = scale * (glyph.width );
        this.height = scale * (glyph.height);

        this.lineY = lineY;
        this.canRender = true;
        this.advanceX = glyph.advanceX * scale;
        this.offsetY = glyph.offset.y * scale;
    }

    public GlyphSprite(float cursorY, float height, float scale, int lineY){
        this.code   = -1;
        this.region = null;
        this.page   = null;
        
        this.x      = 0;
        this.y      = scale * cursorY;
        this.width  = 0;
        this.height = scale * height;

        this.lineY = lineY;
        this.canRender = false;
        this.advanceX = 0;
        this.offsetY = 0;
    }


    public int getCode(){
        return code;
    }

    public Region getRegion(){
        return region;
    }

    public Texture getPage(){
        return page;
    }
    

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }


    public int getLineY(){
        return lineY;
    }

    public boolean isCanRender(){
        return canRender;
    }

    public float getAdvanceX(){
        return advanceX;
    }

    public float getOffsetY(){
        return offsetY;
    }


    public void render(TextureBatch batch, float x, float y, float r, float g, float b, float a){
        if(canRender)
            batch.draw(page, x, y, width, height, region, r, g, b, a);
    }

    public void render(TextureBatch batch){
        render(batch, x, y, 1, 1, 1, 1);
    }

}
