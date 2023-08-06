package pize.tests.minecraftosp.client.audio;

import pize.app.Disposable;
import pize.audio.sound.AudioBuffer;
import pize.audio.sound.AudioSource;
import pize.math.Maths;
import pize.tests.minecraftosp.Minecraft;

public class MusicPlayer implements Disposable{

    private final Minecraft session;

    private final AudioSource source;
    private MusicGroup group;
    private int index;

    public MusicPlayer(Minecraft session){
        this.session = session;
        this.source = new AudioSource();
    }

    public Minecraft getSession(){
        return session;
    }


    public void setGroup(MusicGroup group){
        source.stop();
        if(group == MusicGroup.NONE)
            return;

        this.group = group;
        this.index = Maths.random(0, group.getList().length - 1);
        play();
    }

    private void play(){
        final String musicID = group.getList()[index];
        final AudioBuffer buffer = session.getResources().getAudio(musicID);
        if(buffer == null){
            System.err.println("Music " + musicID + " is not found");
            return;
        }

        source.stop();
        source.setBuffer(buffer);
        source.play(this::playNext);

        System.out.println("Playing: " + musicID + "(" + index + ")");
    }

    private void playNext(){
        index++;
        if(index == group.getList().length)
            index = 0;

        play();
    }

    @Override
    public void dispose(){
        source.dispose();
    }

}
