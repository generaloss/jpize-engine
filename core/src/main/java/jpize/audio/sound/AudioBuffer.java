package jpize.audio.sound;

import jpize.audio.util.AlUtils;
import jpize.files.Resource;
import jpize.util.Disposable;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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


    public void setData(IntBuffer data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }

    public void setData(ByteBuffer data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }

    public void setData(FloatBuffer data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }

    public void setData(ShortBuffer data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }

    public void setData(int[] data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }

    public void setData(float[] data, int channels, int sampleRate){
        int format = AlUtils.getAlFormat(getBits(), channels);
        alBufferData(id, format, data, sampleRate);
    }

    public void setData(short[] data, int channels, int sampleRate){
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

    public float getDurationSeconds(){
        final int size = getSize();
        if(size == 0)
            return 0;

        return (float) size * 8 / (getChannels() * getBits()) / getFrequency();
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
