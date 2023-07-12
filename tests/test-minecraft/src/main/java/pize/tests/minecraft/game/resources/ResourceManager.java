package pize.tests.minecraft.game.resources;

import pize.app.Disposable;
import pize.audio.sound.AudioBuffer;
import pize.audio.sound.Sound;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontCharset;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.tests.minecraft.utils.log.Logger;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager implements Disposable{

    private String location;
    private final Map<String, Resource<?>> resources;
    private final TextureRegion unknownTexture;


    public ResourceManager(){
        resources = new HashMap<>();

        Pixmap unknownPixmap = new Pixmap(2, 2);
        unknownPixmap.clear(1, 0, 1, 1F);
        unknownPixmap.setPixel(0, 0, 0, 0, 0, 1F);
        unknownPixmap.setPixel(1, 1, 0, 0, 0, 1F);
        unknownTexture = new TextureRegion(new Texture(unknownPixmap));
    }


    public TextureRegion getTexture(String id){
        Resource<?> resource = resources.get(id);
        if(resource == null)
            return unknownTexture;
        return (TextureRegion) (resource.isLoaded() ? resource.getResource() : unknownTexture);
    }

    public void putTexture(String id, String location, Rectangle region){
        resources.put(id, new TextureResource(this.location + location, region));
    }

    public void putTexture(String id, String location){
        putTexture(id, location, null);
    }


    public AudioBuffer getSound(String id){
        return (AudioBuffer) resources.get(id).getResource();
    }

    public void putSound(String id, String location){
        resources.put(id, new SoundResource(this.location + location));
    }


    public Sound getMusic(String id){
        return (Sound) resources.get(id).getResource();
    }

    public void putMusic(String id, String location){
        resources.put(id, new MusicResource(this.location + location));
    }


    public BitmapFont getFont(String id){
        return (BitmapFont) resources.get(id).getResource();
    }

    public void putFontFnt(String id, String location){
        resources.put(id, new FontResourceFnt(this.location + location));
    }

    public void putFontTtf(String id, String location, int size){
        resources.put(id, new FontResourceTtf(this.location + location, size));
    }

    public void putFontTtf(String id, String location, int size, FontCharset charset){
        resources.put(id, new FontResourceTtf(this.location + location, size, charset));
    }


    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }


    public void load(){
        for(Resource<?> resource: resources.values()){
            Logger.instance().info("Load " + resource.getClass().getSimpleName());
            resource.loadResource();
        }
    }

    public void reload(){
        for(Resource<?> resource: resources.values())
            if(resource.isLoaded())
                resource.reloadResource();
            else
                resource.loadResource();
    }
    

    @Override
    public void dispose(){
        for(Resource<?> resource: resources.values())
            if(resource.isLoaded())
                resource.dispose();

        unknownTexture.getTexture().dispose();
    }

}
