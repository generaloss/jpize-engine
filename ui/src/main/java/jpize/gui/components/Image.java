package jpize.gui.components;

import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureRegion;
import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.gui.UIComponent;

public class Image extends UIComponent<TextureBatch>{

    private final TextureRegion texture;
    private final Color color;

    public Image(TextureRegion texture){
        this.texture = texture;
        this.color = new Color();
    }

    public Image(Texture texture){
        this(new TextureRegion(texture));
    }

    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        batch.setColor(color);
        batch.draw(texture, x, y, width, height);
        batch.resetColor();
    }

    public void setTexture(TextureRegion texture){
        this.texture.set(texture);
    }

    public void setTexture(Texture texture){
        this.texture.setTexture(texture);
    }

    public Color getColor(){
        return color;
    }

}
