package pize.tests.minecraft.game.resources;

import pize.audio.sound.Sound;
import pize.tests.minecraft.utils.log.Logger;

public class MusicResource extends Resource<Sound>{

    private Sound sound;
    private boolean loaded;

    public MusicResource(String location){
        super(location);
    }

    @Override
    public void loadResource(){
        try{
            sound = new Sound(getLocation());
            loaded = true;
        }catch(RuntimeException e){
            loaded = false;
            Logger.instance().warn(e.getLocalizedMessage());
        }
    }

    @Override
    public void reloadResource(){
        Sound oldSound = sound;
        loadResource();
        oldSound.dispose();
    }

    @Override
    public boolean isLoaded(){
        return loaded;
    }

    @Override
    public Sound getResource(){
        return sound;
    }

    @Override
    public void dispose(){
        sound.dispose();
    }

}

