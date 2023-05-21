package pize.audio.util;

import pize.Pize;
import pize.math.Maths;

import java.nio.ByteBuffer;

public class SoundGenerator{

    private int samplingRate;
    private int channels = 2;
    private int bitsPerSample = 16;


    public SoundGenerator(){
        samplingRate = Pize.audio().getCurrent().getFrequency();
    }


    public ByteBuffer sin(double frequency, double seconds){
        int samples = Maths.round(seconds * samplingRate);
        ByteBuffer buffer = ByteBuffer.allocateDirect(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / samplingRate;

        for(int i = 0; i < samples; i++){
            float sample = Maths.round(Byte.MAX_VALUE * Math.sin(sinPos * Maths.PI2));
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
        int samples = Maths.round(seconds * samplingRate);
        ByteBuffer buffer = ByteBuffer.allocateDirect(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / samplingRate;
        double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            float sample = Maths.round(Byte.MAX_VALUE * Math.sin(sinPos * Maths.PI2));
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
        int samples = Maths.round(seconds * samplingRate);
        ByteBuffer buffer = ByteBuffer.allocateDirect(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / samplingRate;
        double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            float sample = Maths.round(Byte.MAX_VALUE * Math.sin(sinPos * Maths.PI2));
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
        int size = Maths.round(seconds * samplingRate);
        ByteBuffer buffer = ByteBuffer.allocateDirect(size * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / samplingRate;

        for(int i = 0; i < size; i++){
            float sample = Maths.round(Byte.MAX_VALUE * Math.signum(Math.sin(sinPos * Maths.PI2)));
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
        int samples = Maths.round(seconds * samplingRate);
        ByteBuffer buffer = ByteBuffer.allocateDirect(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / samplingRate;
        double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            float sample = Maths.round(Byte.MAX_VALUE * Math.signum(Math.sin(sinPos * Maths.PI2)));
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
        int samples = Maths.round(seconds * samplingRate);
        ByteBuffer buffer = ByteBuffer.allocateDirect(samples * bitsPerSample / 8 * channels);

        double sinPos = 0;
        double sinPosInc = frequency / samplingRate;
        double offset = sinPosInc / samples;

        for(int i = 0; i < samples; i++){
            float sample = Maths.round(Byte.MAX_VALUE * Math.signum(Math.sin(sinPos * Maths.PI2)));
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


    public void setSamplingRate(int samplingRate){
        this.samplingRate = samplingRate;
    }

    public void setChannels(int channels){
        this.channels = channels;
    }

    public void setBitsPerSample(int bitsPerSample){
        this.bitsPerSample = bitsPerSample;
    }


    public int getSamplingRate(){
        return samplingRate;
    }

    public int getChannels(){
        return channels;
    }

    public int getBitsPerSample(){
        return bitsPerSample;
    }

}
