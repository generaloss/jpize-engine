package jpize.audio.io;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import jpize.audio.sound.AudioBuffer;
import jpize.util.res.Resource;
import org.lwjgl.BufferUtils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AudioLoader{

    public static void loadWav(AudioBuffer audioBuffer, Resource resource){
        if(audioBuffer == null)
            return;

        try(final WavInputStream input = new WavInputStream(resource.inStream())){
            
            final byte[] data = input.readAllBytes();
            final ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
            buffer.put(data).flip();
            audioBuffer.setData(buffer, input.channels(), input.sampleRate());
            
        }catch(Exception e){
            throw new RuntimeException("Sound '" + resource.path() + "' reading is failed: " + e.getMessage());
        }
    }

    public static void loadOgg(AudioBuffer audioBuffer, Resource resource){
        if(audioBuffer == null)
            return;

        try(final OggInputStream input = new OggInputStream(resource.inStream())){
            
            final ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
            final byte[] tempBuffer = new byte[2048];
            while(!input.atEnd()){
                int length = input.read(tempBuffer);
                if(length == -1)
                    break;
                output.write(tempBuffer, 0, length);
            }
            final byte[] buffer = output.toByteArray();
            final int bufferSize = buffer.length - (buffer.length % (input.channels() > 1 ? 4 : 2));
            
            final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bufferSize);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.put(buffer);
            byteBuffer.flip();
            audioBuffer.setData(byteBuffer.asShortBuffer(), input.channels(), input.sampleRate());
            
        }catch(Exception e){
            throw new RuntimeException("Audio '" + resource.path() + "' reading is failed: " + e.getMessage());
        }
    }

    public static void loadMp3(AudioBuffer audioBuffer, Resource resource){
        if(audioBuffer == null)
            return;
        
        final ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        final Bitstream bitstream = new Bitstream(resource.inStream());
        final Decoder decoder = new Decoder();

        int sampleRate = -1;
        int channels = -1;

        try{
            while(true){
                final Header header = bitstream.readFrame();
                if(header == null)
                    break;
                
                final SampleBuffer buffer = (SampleBuffer) decoder.decodeFrame(header, bitstream);
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
            
            final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(output.size());
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.put(output.toByteArray());
            byteBuffer.flip();
            audioBuffer.setData(byteBuffer.asShortBuffer(), channels, sampleRate);
        }catch(Exception e){
            throw new RuntimeException("Sound '" + resource.path() + "' reading is failed: " + e.getMessage());
        }
    }

    public static void load(AudioBuffer audioBuffer, Resource resource){
        switch(resource.extension().toLowerCase()){
            case "ogg" -> loadOgg(audioBuffer, resource);
            case "wav" -> loadWav(audioBuffer, resource);
            case "mp3" -> loadMp3(audioBuffer, resource);
            default -> throw new Error("Sound format is not supported: " + resource);
        }
    }

}
