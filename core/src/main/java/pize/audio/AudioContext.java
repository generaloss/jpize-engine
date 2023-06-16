package pize.audio;

import pize.app.Disposable;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;

import static org.lwjgl.openal.ALC11.*;

public class AudioContext implements Disposable{

    private final long id;
    private final AudioDevice device;

    public AudioContext(AudioDevice device){
        this.device = device;
        id = alcCreateContext(device.getId(), new int[]{ 0 });

        long currentContext = alcGetCurrentContext();

        alcMakeContextCurrent(id);
        AL.createCapabilities(ALC.createCapabilities(device.getId()));

        alcMakeContextCurrent(currentContext);
    }

    public void makeCurrent(){
        alcMakeContextCurrent(id);
    }


    public long getId(){
        return id;
    }

    @Override
    public void dispose(){
        alcDestroyContext(id);
    }

}
