package pize.audio.sound;

import pize.Pize;
import pize.app.Disposable;
import pize.math.Mathc;
import pize.math.vecmath.vector.Vec3f;
import pize.util.time.Stopwatch;

import static org.lwjgl.openal.AL11.*;

public class AudioSource implements Disposable{

    protected final int id;
    private AudioStopCallback callback;
    private Stopwatch stopwatch;
    private AudioBuffer buffer;

    public AudioSource(){
        id = alGenSources();
    }


    public void queueBuffers(AudioBuffer[] buffers){
        int[] bufferIds = new int[buffers.length];
        for(int i = 0; i < buffers.length; i++)
            bufferIds[i] = buffers[i].getId();

        alSourceQueueBuffers(id, bufferIds);
    }

    public void queueBuffer(AudioBuffer buffer){
        alSourceQueueBuffers(id, buffer.getId());
    }

    public void unqueueBuffers(AudioBuffer[] buffers){
        int[] bufferIds = new int[buffers.length];
        for(int i = 0; i < buffers.length; i++)
            bufferIds[i] = buffers[i].getId();

        alSourceUnqueueBuffers(id, bufferIds);
    }

    public void unqueueBuffers(){
        alSourceUnqueueBuffers(id);
    }

    public void unqueueBuffer(AudioBuffer buffer){
        alSourceUnqueueBuffers(buffer.getId());
    }

    public int getQueuedBufferCount(){
        return alGetSourcei(id, AL_BUFFERS_QUEUED);
    }

    public int getProcessedBufferCount(){
        return alGetSourcei(id, AL_BUFFERS_PROCESSED);
    }


    public AudioBuffer getBuffer(){
        return buffer;
    }

    public void setBuffer(AudioBuffer buffer){
        this.buffer = buffer;
        if(buffer != null)
            alSourcei(id, AL_BUFFER, buffer.getId());
    }


    public boolean isLooping(){
        return alGetSourcei(id, AL_LOOPING) == 1;
    }

    public float getVolume(){
        return alGetSourcef(id, AL_GAIN);
    }

    public float getPitch(){
        return alGetSourcef(id, AL_PITCH);
    }

    public float getPosition(){
        return alGetSourcef(id, AL_SEC_OFFSET);
    }


    public void setLooping(boolean loop){
        alSourcei(id, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
    }

    public void setVolume(double volume){
        alSourcef(id, AL_GAIN, (float) volume);
    }

    public void setPitch(double pitch){
        alSourcef(id, AL_PITCH, (float) pitch);
    }

    public void setPosition(double seconds){
        alSourcef(id, AL_SEC_OFFSET, (float) seconds);
    }


    public void setRelative(boolean relative){
        alSourcei(id, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
    }


    public void setPosition(Vec3f position){
        alSource3f(id, AL_POSITION, position.x, position.y, position.z);
    }

    public void setPosition(float x, float y, float z){
        alSource3f(id, AL_POSITION, x, y, z);
    }

    public void setVelocity(Vec3f speed){
        alSource3f(id, AL_VELOCITY, speed.x, speed.y, speed.z);
    }

    public void setVelocity(float x, float y, float z){
        alSource3f(id, AL_VELOCITY, x, y, z);
    }


    public void setPan(float pan){
        alSource3f(id, AL_POSITION, Mathc.cos((pan - 1) * Math.PI / 2), 0, Mathc.sin((pan + 1) * Math.PI / 2));
    }


    public boolean isPlaying(){
        return getState() == AL_PLAYING;
    }

    public boolean isPaused(){
        return getState() == AL_PAUSED;
    }

    public boolean isStopped(){
        return getState() == AL_STOPPED;
    }

    public boolean isInitial(){
        return getState() == AL_INITIAL;
    }

    private int getState(){
        return alGetSourcei(id, AL_SOURCE_STATE);
    }


    public void play(){
        alSourcePlay(id);
        playCallback();
    }

    public void pause(){
        alSourcePause(id);
        pauseCallback();
    }

    public void stop(){
        alSourceStop(id);
        stopCallback();
    }


    public void play(AudioStopCallback callback){
        this.callback = callback;
        alSourcePlay(id);

        if(stopwatch == null)
            stopwatch = new Stopwatch().start();
        else
            stopwatch.reset().start();

        Pize.execIf(() -> {
            this.stop();
            callback.onStop();
        }, () -> (stopwatch.getSeconds() >= buffer.getDurationSeconds()));
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
    public void dispose(){
        alDeleteSources(id);
    }

}
