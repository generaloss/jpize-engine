package jpize.al.buffer;

import jpize.al.object.AlObjectInt;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL11.*;

public class AlBuffer extends AlObjectInt{

    public AlBuffer(int ID){
        super(ID);
    }

    public AlBuffer(){
        this(alGenBuffers());
    }


    public int getProperty(AlBufProperty property){
        return alGetBufferi(ID, property.AL);
    }

    public int getFrequency(){
        return getProperty(AlBufProperty.FREQUENCY);
    }

    public int getBits(){
        return getProperty(AlBufProperty.BITS);
    }

    public int getChannels(){
        return getProperty(AlBufProperty.CHANNELS);
    }

    public int getSize(){
        return getProperty(AlBufProperty.SIZE);
    }

    public float getDurationSec(){
        final float size = getSize();
        if(size == 0)
            return 0;
        return size * 8 / (getChannels() * getBits()) / getFrequency();
    }


    public void setData(AlFormat format, ByteBuffer data, int frequency){
        alBufferData(ID, format.AL, data, frequency);
    }

    public void setData(AlFormat format, ShortBuffer data, int frequency){
        alBufferData(ID, format.AL, data, frequency);
    }

    public void setData(AlFormat format, IntBuffer data, int frequency){
        alBufferData(ID, format.AL, data, frequency);
    }

    public void setData(AlFormat format, FloatBuffer data, int frequency){
        alBufferData(ID, format.AL, data, frequency);
    }

    public void setData(AlFormat format, short[] data, int frequency){
        alBufferData(ID, format.AL, data, frequency);
    }

    public void setData(AlFormat format, int[] data, int frequency){
        alBufferData(ID, format.AL, data, frequency);
    }

    public void setData(AlFormat format, float[] data, int frequency){
        alBufferData(ID, format.AL, data, frequency);
    }


    @Override
    public void dispose(){
        alDeleteBuffers(ID);
    }


    public static IntBuffer getIDs(AlBuffer[] buffers){
        final IntBuffer buffersIDs = BufferUtils.createIntBuffer(buffers.length);
        for(AlBuffer alBuffer: buffers)
            buffersIDs.put(alBuffer.getID());

        return buffersIDs;
    }

}
