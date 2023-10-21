package test.midi;

import jpize.audio.sound.AudioBuffer;

import java.util.Arrays;

public class AudioStreamBuffer{

    public final short[] dataBuffer;
    public AudioBuffer audioBuffer;

    public AudioStreamBuffer(int size){
        this.dataBuffer = new short[size];
        this.audioBuffer = new AudioBuffer();
    }

    public void clearData(){
        Arrays.fill(dataBuffer, (short) 0);
    }

    public void updateData(int channels, int sampleRate){
        this.audioBuffer = new AudioBuffer();
        audioBuffer = new AudioBuffer();
        audioBuffer.setData(dataBuffer, channels, sampleRate);
    }

}
