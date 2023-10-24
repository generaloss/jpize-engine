package test;

import jpize.Jpize;
import jpize.audio.sound.Sound;
import jpize.io.context.JpizeApplication;

public class AudioTest extends JpizeApplication{

    public void init(){
        Jpize.context().setShowWindowOnInit(false);

        Sound sound = new Sound("MyMusic.ogg");
        sound.play(Jpize::exit);
    }

}
