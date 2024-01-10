package jpize.audio.sound;

import jpize.al.buffer.AlBuffer;
import jpize.al.buffer.AlFormat;
import jpize.al.buffer.UnsupportedAlFormatException;
import jpize.audio.io.AudioLoader;
import jpize.util.file.Resource;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL11.alGenBuffers;

public class AudioBuffer extends AlBuffer{

    protected AudioBuffer(int ID){
        super(ID);
    }

    public AudioBuffer(){
        this(alGenBuffers());
    }

    public AudioBuffer(Resource res){
        super();
        AudioLoader.load(this, res);
    }

    public AudioBuffer(String filepath){
        this(Resource.internal(filepath));
    }


    public void setData(IntBuffer data, int channels, int frequency) throws UnsupportedAlFormatException{
        super.setData(AlFormat.by(channels, super.getBits()), data, frequency);
    }

    public void setData(ByteBuffer data, int channels, int frequency) throws UnsupportedAlFormatException{
        super.setData(AlFormat.by(channels, super.getBits()), data, frequency);
    }

    public void setData(FloatBuffer data, int channels, int frequency) throws UnsupportedAlFormatException{
        super.setData(AlFormat.by(channels, super.getBits()), data, frequency);
    }

    public void setData(ShortBuffer data, int channels, int frequency) throws UnsupportedAlFormatException{
        super.setData(AlFormat.by(channels, super.getBits()), data, frequency);
    }

    public void setData(int[] data, int channels, int frequency) throws UnsupportedAlFormatException{
        super.setData(AlFormat.by(channels, super.getBits()), data, frequency);
    }

    public void setData(float[] data, int channels, int frequency) throws UnsupportedAlFormatException{
        super.setData(AlFormat.by(channels, super.getBits()), data, frequency);
    }

    public void setData(short[] data, int channels, int frequency) throws UnsupportedAlFormatException{
        super.setData(AlFormat.by(channels, super.getBits()), data, frequency);
    }


    public AudioBuffer copy(){
        return new AudioBuffer(ID);
    }

}
