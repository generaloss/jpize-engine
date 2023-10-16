package jpize.devtests.midi;

import jpize.audio.sound.AudioSource;

public class AudioOutputStream{

    private final int sampleRate;
    private final int channels;
    private final int bufferSamples;
    private final AudioStreamBuffer[] doubleBuffer;
    private int renderBufferIndex;
    private final AudioSource source;

    public AudioOutputStream(int sampleRate, int channels, int bufferSamples){
        this.sampleRate = sampleRate;
        this.channels = channels;
        this.bufferSamples = bufferSamples;

        this.source = new AudioSource();

        this.doubleBuffer = new AudioStreamBuffer[2];
        for(int i = 0; i < 2; i++)
            doubleBuffer[i] = new AudioStreamBuffer(bufferSamples * channels);
    }

    public int getSampleRate(){
        return sampleRate;
    }

    public int getBufferSamples(){
        return bufferSamples;
    }

    public AudioStreamBuffer getRenderBuffer(){
        return doubleBuffer[renderBufferIndex];
    }

    public AudioStreamBuffer getWriteBuffer(){
        return doubleBuffer[1 - renderBufferIndex];
    }

    public void swapBuffers(){
        renderBufferIndex = 1 - renderBufferIndex;
    }

    public void clearWriteBuffer(){
        getWriteBuffer().clearData();
    }

    public void render(){
        final AudioStreamBuffer buffer = getRenderBuffer();

        buffer.updateData(channels, sampleRate);
        source.setBuffer(buffer.audioBuffer);
        source.play();
    }

}
