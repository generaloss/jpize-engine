package jpize.graphics.font.glyph;

import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;

public class GlyphSprite{

    private final Texture page;
    private final Region region;
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    private final int code;
    private final float advanceX;
    private final boolean canRender;

    public GlyphSprite(Glyph glyph, float x, float y, float scale){
        this.page = glyph.pages.get(glyph.pageID);
        this.region = glyph.region;

        this.x = x;
        this.y = y;
        this.width = glyph.width * scale;
        this.height = glyph.height * scale;

        this.code = glyph.code;
        this.advanceX = glyph.advanceX * scale;
        this.canRender = true;
    }

    public GlyphSprite(float y, float height){
        this.page = null;
        this.region = null;

        this.x = 0;
        this.y = y;
        this.width = 0;
        this.height = height;

        this.code = -1;
        this.advanceX = 0;
        this.canRender = false;
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


    public int getCode(){
        return code;
    }

    public float getAdvanceX(){
        return advanceX;
    }

    public boolean isCanRender(){
        return canRender;
    }


    public void render(TextureBatch batch, float x, float y){
        if(canRender)
            batch.draw(page, x, y, width, height, region);
    }

    public void render(TextureBatch batch){
        render(batch, x, y);
    }

}
