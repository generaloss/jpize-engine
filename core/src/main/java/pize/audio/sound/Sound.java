package pize.audio.sound;

import pize.files.Resource;

import java.nio.ByteBuffer;

public class Sound extends AudioSource{

    private final AudioBuffer buffer;

    public Sound(Resource res){
        buffer = new AudioBuffer(res);
        setBuffer(buffer);
    }

    public Sound(String filepath){
        this(new Resource(filepath));
    }

    public Sound(ByteBuffer data, int bitsPerSample, int channels, int sampleRate){
        buffer = new AudioBuffer();
        AudioLoader.load(buffer, data, bitsPerSample, channels, sampleRate);
        setBuffer(buffer);
    }


    public float getDuration(){
        return buffer.getDuration();
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
