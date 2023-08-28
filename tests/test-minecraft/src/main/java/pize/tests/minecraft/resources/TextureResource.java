package pize.tests.minecraft.resources;

import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.tests.minecraft.log.Logger;

import java.awt.*;

public class TextureResource extends Resource<TextureRegion>{

    private TextureRegion texture;
    private Rectangle region;
    private boolean loaded;

    public TextureResource(String location,Rectangle region){
        super(location);

        this.region = region;
    }

    public TextureResource(String location){
        this(location,null);
    }

    @Override
    public void loadResource(){
        try{
            if(region == null)
                texture = new TextureRegion(new Texture(getLocation()));
            else
                texture = new TextureRegion(new Texture(getLocation()), (int) region.getX(), (int) region.getY(), (int) region.getWidth(), (int) region.getHeight());

            loaded = true;
        }catch(RuntimeException e){
            loaded = false;
            Logger.instance().warn(e.getLocalizedMessage());
        }
    }

    @Override
    public void reloadResource(){
        TextureRegion oldTexture = texture;
        loadResource();
        oldTexture.getTexture().dispose();
    }


    public void setRegion(Rectangle region){
        this.region = region;
    }


    @Override
    public boolean isLoaded(){
        return loaded;
    }

    @Override
    public TextureRegion getResource(){
        return texture;
    }

    @Override
    public void dispose(){
        texture.getTexture().dispose();
    }

}
