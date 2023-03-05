package glit.audio.sound;

import glit.audio.util.AlUtils;
import glit.context.Disposable;
import glit.files.FileHandle;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL11.*;

public class AudioBuffer implements Disposable{

    private final int id;

    public AudioBuffer(){
        id = alGenBuffers();
    }

    public AudioBuffer(FileHandle file){
        this();
        AudioLoader.load(this, file);
    }

    public AudioBuffer(String filepath){
        this(new FileHandle(filepath));
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
