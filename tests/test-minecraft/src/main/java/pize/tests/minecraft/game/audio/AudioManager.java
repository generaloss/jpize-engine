package pize.tests.minecraft.game.audio;

import pize.Pize;
import pize.audio.sound.AudioSource;
import pize.app.Disposable;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.options.SoundCategory;

public class AudioManager implements Disposable{

    public static final int MAX_SOUND_NUM = 128;

    private final Session session;
    private final AudioSource[] audioSources;


    public AudioManager(Session session){
        this.session = session;

        audioSources = new AudioSource[MAX_SOUND_NUM];
        for(int i = 0; i < audioSources.length; i++)
            audioSources[i] = new AudioSource();
    }


    public void play(Sound sound, float volume, float pitch){
        for(AudioSource audioSource: audioSources)
            if(!audioSource.isPlaying()){
                audioSource.setBuffer(session.getResourceManager().getSound(sound.resourceId));

                volume *= session.getOptions().getSoundVolume(sound.category) * sound.maxVolume;
                if(sound.category != SoundCategory.MASTER)
                    volume *= session.getOptions().getSoundVolume(SoundCategory.MASTER);

                audioSource.setVolume(volume);
                audioSource.setPitch(pitch);
                audioSource.play();

                return;
            }
    }

    public int getPlayingNum(){
        int playingSoundsNum = 0;
        for(AudioSource audioSource: audioSources)
            if(audioSource.isPlaying())
                playingSoundsNum++;
        return playingSoundsNum;
    }

    public void setDevice(String audioDevice){
        Pize.audio().getDevice(audioDevice).makeCurrent();
        for(AudioSource audioSource: audioSources)
            if(audioSource.isPlaying()){
                audioSource.pause();
                audioSource.play();
            }
    }


    @Override
    public void dispose(){
        for(AudioSource audioSource: audioSources)
            audioSource.dispose();
    }

}
