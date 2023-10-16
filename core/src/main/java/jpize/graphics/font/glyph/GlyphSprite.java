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

    public GlyphSprite(Glyph glyph, float x, float y, float scale){
        this.x = x;
        this.y = y;

        this.page = glyph.pages.get(glyph.page);
        this.region = glyph.region;

        this.width = glyph.width * scale;
        this.height = glyph.height * scale;
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


    public void render(TextureBatch batch, float x, float y){
        batch.draw(page, x, y, getWidth(), getHeight(), region);
    }

    public void render(TextureBatch batch){
        render(batch, x, y);
    }

}
