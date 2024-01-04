package test;

import jpize.audio.sound.Sound;
import jpize.graphics.texture.Texture;
import jpize.io.context.JpizeApplication;

public class ResTest extends JpizeApplication{

    Sound sound = new Sound("audio/MyMusic.ogg");
    Texture texture = new Texture("textures/texture2.png");

    public void dispose(){
        sound.dispose();
        texture.dispose();
    }

}
