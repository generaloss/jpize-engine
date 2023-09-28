package jpize.tests.mcose.client.resources;

import jpize.util.Disposable;
import jpize.audio.sound.AudioBuffer;
import jpize.audio.sound.AudioLoader;
import jpize.files.Resource;
import jpize.graphics.texture.Pixmap;
import jpize.graphics.texture.PixmapIO;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.atlas.TextureAtlas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameResources implements Disposable{

    private final TextureAtlas<String> blockAtlas;
    private final List<Resource> blocksToLoadList;

    private final Map<String, AudioBuffer> audioList;

    public GameResources(){
        blockAtlas = new TextureAtlas<>();
        blocksToLoadList = new ArrayList<>();

        audioList = new HashMap<>();
    }


    public void load(){
        System.out.println("[Client]: Build blocks atlas");

        for(Resource resource: blocksToLoadList){
            final String name = resource.getSimpleName();
            final Pixmap pixmap = PixmapIO.load(resource);
            blockAtlas.put(name, pixmap);
        }

        blockAtlas.generate(32 * 16, 32 * 16);
    }


    public void registerBlockRegion(Resource resource){
        blocksToLoadList.add(resource);
    }

    public void registerBlockRegion(String path, String name){
        registerBlockRegion(new Resource(path + name + ".png"));
    }

    public Region getBlockRegion(String name){
        return blockAtlas.getRegion(name);
    }

    public Texture getBlocks(){
        return blockAtlas.getTexture();
    }


    public void registerSound(String soundID, Resource resource){
        final AudioBuffer buffer = new AudioBuffer();
        AudioLoader.load(buffer, resource);
        audioList.put(soundID, buffer);
    }

    public void registerSound(String soundID, String path, String name){
        registerSound(soundID, new Resource(path + name + ".ogg"));
    }

    public void registerSound(String path, String name){
        final String soundID = name.replace("/", ".");
        registerSound(soundID, path, name);
    }

    public void registerSound(String path, String name, int min, int max){
        for(int i = min; i <= max; i++){
            final String soundID = name.replace("/", ".") + "." + i;
            registerSound(soundID, path, name + i);
        }
    }

    public AudioBuffer getAudio(String soundID){
        return audioList.get(soundID);
    }

    public void registerMusic(String path, String name){
        registerSound(path, name);
    }


    @Override
    public void dispose(){
        blocksToLoadList.clear();
        blockAtlas.getTexture().dispose();
        for(AudioBuffer audio: audioList.values())
            audio.dispose();
    }

}
