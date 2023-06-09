package pize.tests.audio;

import pize.Pize;
import pize.audio.io.WavFile;
import pize.audio.sound.Sound;
import pize.audio.util.SoundGenerator;
import pize.activity.ActivityListener;
import pize.files.Resource;
import pize.io.glfw.Key;

public class Main implements ActivityListener{

    public static void main(String[] args){
        Pize.create("Audio", 700, 150);
        Pize.window().setIcon("icon.png");
        Pize.run(new Main());
    }


    private Sound sound;
    private AudioPlayerUI AudioPlayerUI;
    
    @Override
    public void init(){
        Resource resource = new Resource("Generated.wav", true);
        resource.create();

        // Generate WAV File and Save
        SoundGenerator generator = new SoundGenerator();
        WavFile wavFile = new WavFile(resource.getFile(), generator.getSamplingRate(), generator.getChannels());
        wavFile.setData(generator.sinDown(880, 0.15));
        wavFile.save();

        // Load Generated WAV
        sound = new Sound(resource);
        System.out.println("Generated.wav duration: " + sound.getDuration() + " sec");
        sound.setVolume(0.25F);
        sound.play();
        while(sound.isPlaying());
        sound.dispose();

        // Load OGG
        sound = new Sound("MyMusic.ogg");
        System.out.println("MyMusic.ogg duration: " + sound.getDuration() + " sec");

        // Play audio with GUI
        AudioPlayerUI = new AudioPlayerUI();
        AudioPlayerUI.setSound(sound);
        AudioPlayerUI.play();
    }

    @Override
    public void render(){
        if(Key.ESCAPE.isDown())
            Pize.exit();

        AudioPlayerUI.update();
    }

    @Override
    public void resize(int width, int height){ }

    @Override
    public void dispose(){
        sound.dispose();

        AudioPlayerUI.dispose();
    }

}
