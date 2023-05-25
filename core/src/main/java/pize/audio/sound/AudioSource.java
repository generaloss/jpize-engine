package pize.audio.sound;

import pize.activity.Disposable;
import pize.math.Mathc;
import pize.math.vecmath.tuple.Tuple3f;

import static org.lwjgl.openal.AL11.*;

public class AudioSource implements Disposable{

    protected final int id;

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


    public void setBuffer(AudioBuffer buffer){
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

    public void setVolume(float volume){
        alSourcef(id, AL_GAIN, volume);
    }

    public void setPitch(float pitch){
        alSourcef(id, AL_PITCH, pitch);
    }

    public void setPosition(float seconds){
        alSourcef(id, AL_SEC_OFFSET, seconds);
    }


    public void setRelative(boolean relative){
        alSourcei(id, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
    }


    public void setPosition(Tuple3f position){
        alSource3f(id, AL_POSITION, position.x, position.y, position.z);
    }

    public void setPosition(float x, float y, float z){
        alSource3f(id, AL_POSITION, x, y, z);
    }

    public void setVelocity(Tuple3f speed){
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
    }

    public void pause(){
        alSourcePause(id);
    }

    public void stop(){
        alSourceStop(id);
    }


    @Override
    public void dispose(){
        alDeleteSources(id);
    }

}
