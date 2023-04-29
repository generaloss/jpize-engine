package pize.audio;

import pize.context.Disposable;

import static org.lwjgl.openal.ALC11.*;

public class AudioDevice implements Disposable{

    private final long id;
    private final AudioContext context;

    public AudioDevice(String device){
        id = alcOpenDevice(device);

        context = new AudioContext(this);
    }


    public String getName(){
        return alcGetString(id, ALC_ALL_DEVICES_SPECIFIER);
    }

    public int getStereoSources(){
        return alcGetInteger(id, ALC_STEREO_SOURCES);
    }

    public int getMonoSources(){
        return alcGetInteger(id, ALC_MONO_SOURCES);
    }

    public int getSync(){
        return alcGetInteger(id, ALC_SYNC);
    }

    public int getSamples(){
        return alcGetInteger(id, ALC_CAPTURE_SAMPLES);
    }

    public int getFrequency(){
        return alcGetInteger(id, ALC_FREQUENCY);
    }

    public int getRefreshRate(){
        return alcGetInteger(id, ALC_REFRESH);
    }


    public AudioContext getContext(){
        return context;
    }

    public void makeCurrent(){
        context.makeCurrent();
    }


    public long getId(){
        return id;
    }

    @Override
    public void dispose(){
        context.dispose();
        alcCloseDevice(id);
    }

}
