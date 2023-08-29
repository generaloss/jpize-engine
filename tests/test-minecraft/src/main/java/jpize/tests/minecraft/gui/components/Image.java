package jpize.tests.minecraft.gui.components;

import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureRegion;
import jpize.graphics.util.batch.TextureBatch;

public class Image extends MComponent{
    
    private final TextureRegion texture;
    
    public Image(TextureRegion texture){
        this.texture = texture;
    }
    
    public Image(Texture texture){
        this(new TextureRegion(texture));
    }
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        batch.draw(texture, x, y, width, height);
    }
    
}