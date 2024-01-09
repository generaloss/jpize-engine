package jpize.al.buffer;

import javax.sound.sampled.AudioFormat;

import static org.lwjgl.openal.AL11.*;

public enum AlFormat{

    MONO8   (AL_FORMAT_MONO8   , 1, 8 ),
    MONO16  (AL_FORMAT_MONO16  , 1, 16),
    STEREO8 (AL_FORMAT_STEREO8 , 2, 8 ),
    STEREO16(AL_FORMAT_STEREO16, 2, 16);

    public final int AL;
    private final int channels;
    private final int bits;

    AlFormat(int AL, int channels, int bits){
        this.AL = AL;
        this.channels = channels;
        this.bits = bits;
    }

    public int getChannels(){
        return channels;
    }

    public int getBits(){
        return bits;
    }


    public static AlFormat fromAL(int AL){
        return values()[AL - MONO8.AL];
    }

    public static AlFormat from(int channels, int bits) throws UnsupportedAlFormatException{
        if(!(bits == 16 || bits == 8) || !(channels == 2 || channels == 1))
            throw new UnsupportedAlFormatException("Unsupported format (" + bits + " bits, " + channels + " channels)");

        return valueOf((channels == 2 ? "STEREO" : "MONO") + bits);
    }

    public static AlFormat from(AudioFormat format) throws UnsupportedAlFormatException{
        return from(format.getChannels(), format.getSampleSizeInBits());
    }

}
