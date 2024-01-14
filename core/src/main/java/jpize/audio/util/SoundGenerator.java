package jpize.audio.util;

import org.lwjgl.BufferUtils;
import jpize.Jpize;
import jpize.util.math.Maths;

import java.nio.ByteBuffer;

public class SoundGenerator{

    private int sampleRate;
    private int channels = 2;
    private int bitsPerSample = 16;


    public SoundGenerator(){
        this.sampleRate = Jpize.audio().getCurrentDevice().getFrequency();
    }


    public ByteBuffer sin(double frequency, double seconds){
        final int samples = Maths.round(seconds * sampleRate);
        final ByteBuffer buffer = BufferUtils.createByteBuffer(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        final double sinPosInc = frequency / sampleRate;

        for(int i = 0; i < samples; i++){
            final float sample = Maths.round(Byte.MAX_VALUE * Math.sin(sinPos * Maths.twoPI));
            for(int j = 0; j < channels; j++)
                switch(bitsPerSample){
                    case 16 -> buffer.putShort((short) sample);
                    case 8 -> buffer.put((byte) sample);
                }

            sinPos += sinPosInc;
            if(sinPos > 1)
                sinPos -= 1;
        }

        buffer.flip();
        return buffer;
    }

    public ByteBuffer sinDown(double frequency, double seconds){
        final int samples = Maths.round(seconds * sampleRate);
        final ByteBuffer buffer = BufferUtils.createByteBuffer(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / sampleRate;
        final double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            final float sample = Maths.round(Byte.MAX_VALUE * Math.sin(sinPos * Maths.twoPI));
            for(int j = 0; j < channels; j++)
                switch(bitsPerSample){
                    case 16 -> buffer.putShort((short) sample);
                    case 8 -> buffer.put((byte) sample);
                }

            sinPos += sinPosInc;
            if(sinPos > 1)
                sinPos -= 1;

            sinPosInc -= offset;
        }

        buffer.flip();
        return buffer;
    }

    public ByteBuffer sinUp(double frequency, double seconds){
        final int samples = Maths.round(seconds * sampleRate);
        final ByteBuffer buffer = BufferUtils.createByteBuffer(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / sampleRate;
        final double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            final float sample = Maths.round(Byte.MAX_VALUE * Math.sin(sinPos * Maths.twoPI));
            for(int j = 0; j < channels; j++)
                switch(bitsPerSample){
                    case 16 -> buffer.putShort((short) sample);
                    case 8 -> buffer.put((byte) sample);
                }

            sinPos += sinPosInc;
            if(sinPos > 1)
                sinPos -= 1;

            sinPosInc += offset;
        }

        buffer.flip();
        return buffer;
    }

    public ByteBuffer square(double frequency, double seconds){
        final int size = Maths.round(seconds * sampleRate);
        final ByteBuffer buffer = BufferUtils.createByteBuffer(size * bitsPerSample / 8 * channels);

        double sinPos = 0;
        final double sinPosInc = frequency / sampleRate;

        for(int i = 0; i < size; i++){
            final float sample = Maths.round(Byte.MAX_VALUE * Math.signum(Math.sin(sinPos * Maths.twoPI)));
            for(int j = 0; j < channels; j++)
                switch(bitsPerSample){
                    case 16 -> buffer.putShort((short) sample);
                    case 8 -> buffer.put((byte) sample);
                }

            sinPos += sinPosInc;
            if(sinPos > 1)
                sinPos -= 1;
        }

        buffer.flip();
        return buffer;
    }

    public ByteBuffer squareDown(double frequency, double seconds){
        final int samples = Maths.round(seconds * sampleRate);
        final ByteBuffer buffer =BufferUtils.createByteBuffer(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / sampleRate;
        final double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            final float sample = Maths.round(Byte.MAX_VALUE * Math.signum(Math.sin(sinPos * Maths.twoPI)));
            for(int j = 0; j < channels; j++)
                switch(bitsPerSample){
                    case 16 -> buffer.putShort((short) sample);
                    case 8 -> buffer.put((byte) sample);
                }

            sinPos += sinPosInc;
            if(sinPos > 1)
                sinPos -= 1;

            sinPosInc -= offset;
        }

        buffer.flip();
        return buffer;
    }

    public ByteBuffer squareUp(double frequency, double seconds){
        final int samples = Maths.round(seconds * sampleRate);
        final ByteBuffer buffer = BufferUtils.createByteBuffer(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / sampleRate;
        final double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            final float sample = Maths.round(Byte.MAX_VALUE * Math.signum(Math.sin(sinPos * Maths.twoPI)));
            for(int j = 0; j < channels; j++)
                switch(bitsPerSample){
                    case 16 -> buffer.putShort((short) sample);
                    case 8 -> buffer.put((byte) sample);
                }

            sinPos += sinPosInc;
            if(sinPos > 1)
                sinPos -= 1;

            sinPosInc += offset;
        }

        buffer.flip();
        return buffer;
    }


    public void setSampleRate(int samplingRate){
        this.sampleRate = samplingRate;
    }

    public void setChannels(int channels){
        this.channels = channels;
    }

    public void setBitsPerSample(int bitsPerSample){
        this.bitsPerSample = bitsPerSample;
    }


    public int getSampleRate(){
        return sampleRate;
    }

    public int getChannels(){
        return channels;
    }

    public int getBitsPerSample(){
        return bitsPerSample;
    }

}
