package jpize.audio.sound;

import jpize.al.buffer.AlFormat;
import jpize.al.buffer.UnsupportedAlFormatException;
import jpize.util.file.Resource;

import java.nio.ByteBuffer;

public class Sound extends AudioSource{

    private final AudioBuffer buffer;

    public Sound(Resource res){
        this.buffer = new AudioBuffer(res);
        super.setBuffer(buffer);
    }

    public Sound(String filepath){
        this(Resource.internal(filepath));
    }

    public Sound(ByteBuffer data, int bits, int channels, int frequency){
        this.buffer = new AudioBuffer();
        try{
            buffer.setData(AlFormat.by(channels, bits), data, frequency);
            super.setBuffer(buffer);

        }catch(UnsupportedAlFormatException e){
            System.err.println(e.getLocalizedMessage());
        }
    }


    public float getDuration(){
        return buffer.getDurationSec();
    }

    public AudioBuffer getBuffer(){
        return buffer;
    }

    @Override
    public void dispose(){
        buffer.dispose();
        super.dispose();
    }

}
