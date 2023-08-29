package jpize.tests.minecraft.audio;

import jpize.audio.sound.Sound;
import jpize.util.Disposable;
import jpize.math.Maths;
import jpize.tests.minecraft.Session;
import jpize.tests.minecraft.options.SoundCategory;
import jpize.util.Utils;
import jpize.util.time.JpizeRunnable;

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

        new JpizeRunnable(() -> {
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
