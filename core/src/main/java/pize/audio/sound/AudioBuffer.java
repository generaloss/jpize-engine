package pize.audio.sound;

import pize.audio.util.AlUtils;
import pize.app.Disposable;
import pize.files.Resource;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL11.*;

public class AudioBuffer implements Disposable{

    private final int id;

    public AudioBuffer(){
        id = alGenBuffers();
    }

    public AudioBuffer(Resource res){
        this();
        AudioLoader.load(this, res);
    }

    public AudioBuffer(String filepath){
        this(new Resource(filepath));
    }


    public void setData(ByteBuffer data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }

    public void setData(ShortBuffer data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }


    public int getFrequency(){
        return alGetBufferi(id, AL_FREQUENCY);
    }

    public int getBits(){
        return alGetBufferi(id, AL_BITS);
    }

    public int getChannels(){
        return alGetBufferi(id, AL_CHANNELS);
    }

    public int getSize(){
        return alGetBufferi(id, AL_SIZE);
    }

    public float getDuration(){
        return (float) getSize() * 8 / (getChannels() * getBits()) / getFrequency();
    }


    public int getId(){
        return id;
    }

    @Override
    public void dispose(){
        alDeleteBuffers(id);
    }

    @Override
    public int hashCode(){
        return id * 11;
    }

}
