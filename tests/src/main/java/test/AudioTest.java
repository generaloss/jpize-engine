package test;

import jpize.Jpize;
import jpize.al.Al;
import jpize.al.buffer.AlFormat;
import jpize.al.device.AlCaptureDevice;
import jpize.al.device.AlDevice;
import jpize.audio.io.WavFile;
import jpize.audio.sound.AudioBuffer;
import jpize.audio.sound.AudioSource;
import jpize.audio.sound.Sound;
import jpize.app.JpizeApplication;
import jpize.util.Utils;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class AudioTest extends JpizeApplication{

    private Sound sound;
    private AudioSource source;

    public void init(){
        Jpize.context().setShowWindowOnInit(false);
        //test();
        //record();
        play();
    }

    private void record(){
        final int freq = 48000;
        final AlFormat format = AlFormat.MONO16;

        AlCaptureDevice captureDevice = new AlCaptureDevice(Al.getDefaultCaptureDeviceName(), freq, format, freq * 5);

        System.out.println(3);
        Utils.delayElapsed(1F);
        System.out.println(2);
        Utils.delayElapsed(1F);
        System.out.println(1);
        Utils.delayElapsed(1F);

        System.out.println("start");
        captureDevice.start();
        Utils.delayElapsed(5F);
        System.out.println("stop");
        captureDevice.stop();

        final ByteBuffer buffer = BufferUtils.createByteBuffer(captureDevice.getCaptureSamples() * 2);
        captureDevice.getSamples(buffer);

        sound = new Sound(buffer, format.getBits(), format.getChannels(), freq);
        sound.play(Jpize::exit);

        WavFile wavFile = new WavFile("record.wav", freq, format.getChannels());
        wavFile.setData(buffer);
        wavFile.save();
    }

    private void test(){
        AlDevice device = new AlDevice("Family 17h (Models 00h-0fh) HD Audio Controller Цифровой стерео (IEC958)");

        System.out.println("Renderer -- " + Al.getRenderer());
        System.out.println("Specifier -- " + Al.getSpecifier());
        System.out.println("DefaultDeviceSpecifier -- " + Al.getDefaultDeviceSpecifier());
        System.out.println("CaptureDeviceName -- " + Al.getCaptureDeviceName());
        System.out.println("DefaultCaptureDeviceName -- " + Al.getDefaultCaptureDeviceName());

        System.out.println();

        System.out.println("Specifier -- " + device.getSpecifier());
        System.out.println("CaptureSpecifier -- " + device.getCaptureSpecifier());
        System.out.println("Extensions -- " + device.getExtensions());

        System.out.println();

        System.out.println("AllDevicesSpecifier -- " + device.getName());
        System.out.println("StereoSources -- " + device.getStereoSources());
        System.out.println("MonoSources -- " + device.getMonoSources());
        System.out.println("Sync -- " + device.getSync());
        System.out.println("Frequency -- " + device.getFrequency());
        System.out.println("Refresh -- " + device.getRefreshRate());
    }

    private void play(){
        // sound = new Sound("audio/MyMusic.ogg");
        // sound.setRelative(true);
        // sound.setPosition(10000000, 0, 0);
        // sound.play(Jpize::exit);

        source = new AudioSource();
        AudioBuffer buffer = new AudioBuffer("audio/MyMusic.ogg");
        source.queueBuffer(buffer);
        source.queueBuffer(buffer);

        source.setRelative(true);
        source.play();
    }

    private float angle;

    public void update(){
        angle += 0.001F;
        source.setPan(angle);
        System.out.println(source.getBuffersQueued() + " : " + source.getBuffersProcessed());
    }

    public void dispose(){
        // sound.dispose();
    }

}
