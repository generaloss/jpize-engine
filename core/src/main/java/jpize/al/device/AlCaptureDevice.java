package jpize.al.device;

import jpize.al.buffer.AlFormat;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.ALC11.*;

public class AlCaptureDevice extends AlAbstractDevice {

    public AlCaptureDevice(CharSequence deviceSpecifier, int frequency, AlFormat format, int samples){
        super(alcCaptureOpenDevice(deviceSpecifier, frequency, format.AL, samples));
    }


    public void start(){
        alcCaptureStart(ID);
    }

    public void stop(){
        alcCaptureStop(ID);
    }


    public void getSamples(short[] buffer, int samples){
        alcCaptureSamples(ID, buffer, samples);
    }

    public void getSamples(int[] buffer, int samples){
        alcCaptureSamples(ID, buffer, samples);
    }

    public void getSamples(float[] buffer, int samples){
        alcCaptureSamples(ID, buffer, samples);
    }

    public void getSamples(ByteBuffer buffer, int samples){
        alcCaptureSamples(ID, buffer, samples);
    }

    public void getSamples(ShortBuffer buffer, int samples){
        alcCaptureSamples(ID, buffer, samples);
    }

    public void getSamples(IntBuffer buffer, int samples){
        alcCaptureSamples(ID, buffer, samples);
    }

    public void getSamples(FloatBuffer buffer, int samples){
        alcCaptureSamples(ID, buffer, samples);
    }


    public void getSamples(short[] buffer){
        getSamples(buffer, super.getSamples());
    }

    public void getSamples(int[] buffer){
        getSamples(buffer, super.getSamples());
    }

    public void getSamples(float[] buffer){
        getSamples(buffer, super.getSamples());
    }

    public void getSamples(ByteBuffer buffer){
        getSamples(buffer, super.getSamples());
    }

    public void getSamples(ShortBuffer buffer){
        getSamples(buffer, super.getSamples());
    }

    public void getSamples(IntBuffer buffer){
        getSamples(buffer, super.getSamples());
    }

    public void getSamples(FloatBuffer buffer){
        getSamples(buffer, super.getSamples());
    }


    @Override
    public void dispose(){
        alcCaptureCloseDevice(ID);
    }

}
