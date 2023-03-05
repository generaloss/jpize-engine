package glit.audio.util;

import javax.sound.sampled.AudioFormat;

import static org.lwjgl.openal.AL10.*;

public class AlUtils{

    public static int getAlFormat(int bitsPerSample, int channels) throws Error{
        if(bitsPerSample == 16){
            if(channels == 1)
                return AL_FORMAT_MONO16;
            else if(channels == 2)
                return AL_FORMAT_STEREO16;
        }else if(bitsPerSample == 8){
            if(channels == 1)
                return AL_FORMAT_MONO8;
            else if(channels == 2)
                return AL_FORMAT_STEREO8;
        }

        throw new Error("Unsupported audio format");
    }

    public static int getAlFormat(AudioFormat format){
        return getAlFormat(format.getSampleSizeInBits(), format.getChannels());
    }

}
