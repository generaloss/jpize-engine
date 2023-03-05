package glit.audio.sound;

import glit.audio.io.OggInputStream;
import glit.audio.io.WavInputStream;
import glit.audio.util.AlUtils;
import glit.files.FileHandle;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import org.lwjgl.BufferUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.lwjgl.openal.AL11.alBufferData;

public class AudioLoader{

    public static void loadWav(AudioBuffer audioBuffer, FileHandle file){
        if(audioBuffer == null)
            return;

        try{
            WavInputStream input = new WavInputStream(file.input());
            byte[] data = input.readAllBytes();

            ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
            buffer.put(data).flip();
            audioBuffer.setData(buffer, input.channels(), input.sampleRate());

            input.close();
        }catch(Exception e){
            throw new RuntimeException("Sound '" + file.getPath() + "' reading is failed: " + e.getMessage());
        }
    }

    public static void loadOgg(AudioBuffer audioBuffer, FileHandle file){
        if(audioBuffer == null)
            return;

        try{
            OggInputStream input = new OggInputStream(file.input());

            ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
            byte[] tempBuffer = new byte[2048];
            while(!input.atEnd()){
                int length = input.read(tempBuffer);
                if(length == -1)
                    break;
                output.write(tempBuffer, 0, length);
            }
            byte[] buffer = output.toByteArray();
            int bufferSize = buffer.length - (buffer.length % (input.channels() > 1 ? 4 : 2));

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferSize);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.put(buffer);
            byteBuffer.flip();
            audioBuffer.setData(byteBuffer.asShortBuffer(), input.channels(), input.sampleRate());

            input.close();
        }catch(IOException e){
            throw new RuntimeException("Sound '" + file.getPath() + "' reading is failed: " + e.getMessage());
        }
    }

    public static void loadMp3(AudioBuffer audioBuffer, FileHandle file){
        if(audioBuffer == null)
            return;

        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        Bitstream bitstream = new Bitstream(file.input());
        Decoder decoder = new Decoder();

        int sampleRate = -1;
        int channels = -1;

        try{
            while(true){
                Header header = bitstream.readFrame();
                if(header == null)
                    break;

                SampleBuffer buffer = (SampleBuffer) decoder.decodeFrame(header, bitstream);
                for(short value: buffer.getBuffer()){
                    output.write(value & 0xff);
                    output.write((value >> 8) & 0xff);
                }

                if(channels == -1){
                    channels = header.mode() == Header.SINGLE_CHANNEL ? 1 : 2;
                    sampleRate = decoder.getOutputFrequency();
                }

                bitstream.closeFrame();
            }

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(output.size());
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.put(output.toByteArray());
            byteBuffer.flip();
            audioBuffer.setData(byteBuffer.asShortBuffer(), channels, sampleRate);
        }catch(Throwable e){
            throw new RuntimeException("Sound '" + file.getPath() + "' reading is failed: " + e.getMessage());
        }
    }

    public static void load(AudioBuffer audioBuffer, FileHandle file){
        switch(file.extension().toLowerCase()){
            case "ogg" -> loadOgg(audioBuffer, file);
            case "wav" -> loadWav(audioBuffer, file);
            case "mp3" -> loadMp3(audioBuffer, file);
            default -> throw new Error("Sound format is not supported: " + file);
        }
    }

    public static void load(AudioBuffer audioBuffer, ByteBuffer data, int bitsPerSample, int channels, int sampleRate){
        if(audioBuffer == null)
            return;

        int format = AlUtils.getAlFormat(bitsPerSample, channels);
        alBufferData(audioBuffer.getId(), format, data, sampleRate);
    }

}
