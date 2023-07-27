package pize.tests.voxelgame.client.audio;

import pize.app.Disposable;
import pize.audio.sound.AudioBuffer;
import pize.audio.sound.AudioSource;
import pize.tests.voxelgame.Minecraft;
import pize.tests.voxelgame.main.audio.Sound;

public class AudioPlayer implements Disposable{

    private final Minecraft session;
    private final AudioSource[] sources;

    public AudioPlayer(Minecraft session){
        this.session = session;

        System.out.println("[Client]: Initialize audio player");

        this.sources = new AudioSource[256];
        for(int i = 0; i < sources.length; i++)
            sources[i] = new AudioSource();
    }


    public void play(Sound sound, float volume, float pitch, float x, float y, float z){
        System.out.println("Play: " + sound.getID());
        play(session.getResources().getSound(sound.getID()), volume, pitch, x, y, z);
    }

    public void play(String soundID, float volume, float pitch, float x, float y, float z){
        System.out.println("Play: " + soundID);
        play(session.getResources().getSound(soundID), volume, pitch, x, y, z);
    }

    public void play(AudioBuffer buffer, float volume, float pitch, float x, float y, float z){
        if(buffer == null)
            return;

        final int sourceIndex = getFirstFreeIndex();
        if(sourceIndex == -1)
            return;

        final AudioSource source = sources[sourceIndex];
        source.setBuffer(buffer);
        source.setVolume(volume);
        source.setPitch(pitch);
        source.setPosition(x, y, z);
        source.play();
    }

    private int getFirstFreeIndex(){
        for(int i = 0; i < sources.length; i++)
            if(!sources[i].isPlaying())
                return i;

        return -1;
    }


    @Override
    public void dispose(){
        for(AudioSource source: sources)
            source.dispose();
    }

}
