package pize.gui.components;

import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.batch.Batch;
import pize.gui.UIComponent;

public class Image extends UIComponent<Batch>{

    private final TextureRegion texture;

    public Image(TextureRegion texture){
        this.texture = texture;
    }

    public Image(Texture texture){
        this(new TextureRegion(texture));
    }

    @Override
    protected void render(Batch batch, float x, float y, float width, float height){
        batch.draw(texture, x, y, width, height);
    }

}
