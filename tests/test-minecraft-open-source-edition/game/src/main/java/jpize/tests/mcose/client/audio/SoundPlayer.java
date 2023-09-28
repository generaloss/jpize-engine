package jpize.tests.mcose.client.audio;

import jpize.util.Disposable;
import jpize.audio.sound.AudioBuffer;
import jpize.audio.sound.AudioSource;
import jpize.tests.mcose.client.Minecraft;
import jpize.tests.mcose.main.audio.Sound;

public class SoundPlayer implements Disposable{

    public static final int MAX_SOUND_SOURCES = 64;


    private final Minecraft session;
    private final AudioSource[] sources;

    public SoundPlayer(Minecraft session){
        this.session = session;

        System.out.println("[Client]: Initialize audio player");

        this.sources = new AudioSource[MAX_SOUND_SOURCES];
        for(int i = 0; i < sources.length; i++)
            sources[i] = new AudioSource();
    }


    public void play(Sound sound, float volume, float pitch, float x, float y, float z){
        final AudioBuffer buffer = session.getResources().getAudio(sound.getID());
        if(buffer == null){
            System.err.println("Sound " + sound.getID() + " is not found");
            return;
        }
        play(buffer, volume, pitch * sound.getPitch(), x, y, z);
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


    public int getPlayingSoundsNum(){
        int playing = 0;
        for(AudioSource source: sources)
            if(source.isPlaying())
                playing++;

        return playing;
    }


    @Override
    public void dispose(){
        for(AudioSource source: sources)
            source.dispose();
    }

}
