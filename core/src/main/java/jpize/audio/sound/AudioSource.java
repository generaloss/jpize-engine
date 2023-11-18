package jpize.audio.sound;

import jpize.Jpize;
import jpize.al.source.AlSource;
import jpize.math.Mathc;
import jpize.math.vecmath.vector.Vec3f;
import jpize.util.time.Stopwatch;

import static org.lwjgl.openal.AL11.*;

public class AudioSource extends AlSource{

    private AudioStopCallback callback;
    private Stopwatch stopwatch;

    protected AudioSource(int ID){
        super(ID);
    }

    public AudioSource(){
        this(alGenSources());
    }


    public void setPitch(double pitch){
        super.setPitch((float) pitch);
    }

    public void setOffset(double seconds){
        super.setSecOffset((float) seconds);
    }


    public void setPosition(Vec3f position){
        alSource3f(ID, AL_POSITION, position.x, position.y, position.z);
    }

    public void setVelocity(Vec3f speed){
        alSource3f(ID, AL_VELOCITY, speed.x, speed.y, speed.z);
    }


    public void setPan(float pan){
        alSource3f(ID, AL_POSITION, Mathc.cos((pan - 1) * Math.PI / 2), 0, Mathc.sin((pan + 1) * Math.PI / 2));
    }


    public void play(){
        super.play();
        playCallback();
    }

    public void pause(){
        super.pause();
        pauseCallback();
    }

    public void stop(){
        super.stop();
        stopCallback();
    }


    public void play(AudioStopCallback callback){
        this.callback = callback;
        alSourcePlay(ID);

        if(stopwatch == null)
            stopwatch = new Stopwatch().start();
        else
            stopwatch.reset().start();

        Jpize.execIf(() -> {
            this.stop();
            callback.onStop();
        }, () -> (stopwatch.getSeconds() >= getBuffer().getDurationSec()));
    }

    private void pauseCallback(){
        if(callback == null)
            return;

        stopwatch.pause();
    }

    private void stopCallback(){
        if(callback == null)
            return;

        stopwatch.stop();
    }

    private void playCallback(){
        if(callback == null)
            return;

        stopwatch.resume();
    }


    @Override
    public AudioSource copy(){
        return new AudioSource(ID);
    }

}
