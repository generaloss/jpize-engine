package pize.audio.io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class WavFile{

    public static final int BITS_PER_SAMPLE = 16;

    private final File file;
    private final int sampleRate;
    private final short channels;
    private byte[] data;

    public WavFile(File file, int sampleRate, int channels){
        this.file = file;
        this.sampleRate = sampleRate;
        this.channels = (short) channels;
    }

    public WavFile(String path, int sampleRate, int channels){
        this(new File(path), sampleRate, channels);
    }


    public void save(){
        try(final DataOutputStream out = new DataOutputStream(new FileOutputStream(file))){
            
            { // Chunk Descriptor
                out.writeBytes("RIFF");                                                          // ID
                out.writeInt(Integer.reverseBytes(20 + 16 + Integer.reverseBytes(data.length))); // Size (Не обязательно)

                out.writeBytes("WAVE");                                                          // Format
            }

            { // Ftm SubChunk
                out.writeBytes("fmt ");                                                          // ID
                out.writeInt(Integer.reverseBytes(16));                                          // Size (16 for PCM audio format)

                out.writeShort(Short.reverseBytes((short) 1));                                   // Audio Format (PCM = 1)
                out.writeShort(Short.reverseBytes(channels));                                    // Num Channels
                out.writeInt(Integer.reverseBytes(sampleRate));                                  // Sample Rate
                out.writeInt(Integer.reverseBytes(sampleRate * channels * BITS_PER_SAMPLE / 8)); // Byte Rate (Не обязательно)
                out.writeShort(Short.reverseBytes((short) (channels * BITS_PER_SAMPLE / 8)));    // Block Align
                out.writeShort(Short.reverseBytes((short) BITS_PER_SAMPLE));                     // Bits Per Sample (16)
            }

            { // Data SubChunk
                out.writeBytes("data");                                                          // ID
                out.writeInt(Integer.reverseBytes(data.length));                                 // Size

                out.write(data);                                                                 // Data
            }

        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setData(byte[] data){
        this.data = data;
    }

    public void setData(ByteBuffer buffer){
        data = new byte[buffer.remaining()];
        for(int i = 0; buffer.hasRemaining(); i++)
            data[i] = buffer.get();
    }

}
