package jpize.ui.component;

import jpize.graphics.texture.Texture;
import jpize.graphics.util.TextureUtils;
import jpize.util.color.Color;

public class UIBackground{

    private Texture image;
    private final Color color;

    public UIBackground(Texture image, Color color){
        this.image = image;
        this.color = color;
    }

    public UIBackground(Texture image){
        this(image, new Color(1, 1, 1, 1));
    }

    public UIBackground(Color color){
        this(TextureUtils.quadTexture(), color);
    }

    public UIBackground(){
        this(TextureUtils.quadTexture(), new Color(1, 1, 1, 1));
    }


    public Texture getImage(){
        return image;
    }

    public void setImage(Texture image){
        this.image = image;
    }

    public Color color(){
        return color;
    }

}
