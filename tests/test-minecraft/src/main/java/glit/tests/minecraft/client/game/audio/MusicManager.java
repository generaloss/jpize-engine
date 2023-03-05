package glit.tests.minecraft.client.game.audio;

import glit.audio.sound.Sound;
import glit.context.Disposable;
import glit.math.Maths;
import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.client.game.options.SoundCategory;
import glit.util.Utils;
import glit.util.time.GlitRunnable;

public class MusicManager implements Disposable{

    private final Session session;

    private MusicSet currentSet;
    private int currentIndex;
    private Sound current;

    public MusicManager(Session session){
        this.session = session;
        setCurrentSet(MusicSet.MAIN_MENU);
    }

    public void updateVolumeForPlaying(){
        if(current != null)
            current.setVolume(getVolume());
    }

    private float getVolume(){
        return session.getOptions().getSoundVolume(SoundCategory.MUSIC)
            * session.getOptions().getSoundVolume(SoundCategory.MASTER);
    }


    public void setCurrentSet(MusicSet set){
        this.currentSet = set;
        if(set == null)
            return;

        currentIndex = Maths.random(currentSet.size() - 1);
        play();
    }

    private void play(){
        if(current != null)
            current.stop();

        current = session.getResourceManager().getMusic(currentSet.get(currentIndex));
        if(current == null)
            return;

        current.setVolume(getVolume());
        current.play();

        new GlitRunnable(){
            @Override
            public void run(){
                Utils.delayMillis(1);
                next();
            }
        }.runLaterAsync((long)(current.getDuration() * 1000));
    }

    private void next(){
        currentIndex++;
        if(currentIndex >= currentSet.size())
            currentIndex = 0;

        play();
    }


    @Override
    public void dispose(){
        current.dispose();
    }

}
