package glit.gui.components;

import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureRegion;
import glit.graphics.util.batch.Batch;
import glit.gui.UIComponent;

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
