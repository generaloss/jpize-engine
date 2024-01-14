package jpize.audio.sound;

import jpize.Jpize;
import jpize.al.source.AlSource;
import jpize.util.math.Mathc;
import jpize.util.math.vecmath.vector.Vec3f;
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


    public void setGain(double gain){
        super.setGain((float) gain);
    }

    public void setMaxGain(double maxGain){
        super.setMaxGain((float) maxGain);
    }

    public void setMinGain(double minGain){
        super.setMinGain((float) minGain);
    }

    public void setConeOuterGain(double coneOuterGain){
        super.setConeOuterGain((float) coneOuterGain);
    }

    public void setConeInnerAngle(double coneInnerAngle){
        super.setConeInnerAngle((float) coneInnerAngle);
    }

    public void setConeOuterAngle(double coneOuterAngle){
        super.setConeOuterAngle((float) coneOuterAngle);
    }

    public void setPitch(double pitch){
        super.setPitch((float) pitch);
    }

    public void setSecOffset(double seconds){
        super.setSecOffset((float) seconds);
    }

    public void setSampleOffset(double seconds){
        super.setSampleOffset((float) seconds);
    }

    public void setByteOffset(double seconds){
        super.setByteOffset((float) seconds);
    }


    public void setPosition(Vec3f position){
        super.setPosition(position.x, position.y, position.z);
    }

    public void setPosition(double x, double y, double z){
        super.setPosition((float) x, (float) y, (float) z);
    }

    public void setVelocity(Vec3f velocity){
        super.setVelocity(velocity.x, velocity.y, velocity.z);
    }

    public void setVelocity(double x, double y, double z){
        super.setVelocity((float) x, (float) y, (float) z);
    }

    public void setDirection(Vec3f direction){
        super.setVelocity(direction.x, direction.y, direction.z);
    }

    public void setDirection(double x, double y, double z){
        super.setDirection((float) x, (float) y, (float) z);
    }


    public void setRolloffFactor(double rolloffFactor){
        super.setRolloffFactor((float) rolloffFactor);
    }

    public void setMaxDistance(double maxDistance){
        super.setMaxDistance((float) maxDistance);
    }

    public void setReferenceDistance(double referenceDistance){
        super.setReferenceDistance((float) referenceDistance);
    }


    public void setPan(float pan){
        super.setPosition(Mathc.cos((pan - 1) * Math.PI / 2), 0, Mathc.sin((pan + 1) * Math.PI / 2));
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
