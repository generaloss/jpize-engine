package pize.tests.minecraft.game.audio;

import pize.audio.sound.Sound;
import pize.app.Disposable;
import pize.math.Maths;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.options.SoundCategory;
import pize.util.Utils;
import pize.util.time.PizeRunnable;

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

        new PizeRunnable(() -> {
            Utils.delayMillis(1);
            next();
        }).runLaterAsync((long)(current.getDuration() * 1000));
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
