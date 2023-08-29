package jpize.audio.io;

import java.io.*;

public class WavInputStream extends FilterInputStream{

    private final int channels, sampleRate;
    private int availableBytes;

    public WavInputStream(InputStream inputStream) throws Exception{
        super(inputStream);

        // Header
        if(read() != 'R' || read() != 'I' || read() != 'F' || read() != 'F')
            throw new RuntimeException("RIFF header not found");

        skipFully(4);

        if(read() != 'W' || read() != 'A' || read() != 'V' || read() != 'E')
            throw new RuntimeException("Invalid wave file header");

        int fmtChunkLength = seekToChunk('f', 'm', 't', ' ');

        // Audio Format
        int format = read() & 0xFF | (read() & 0xFF) << 8;
        if(format != 1) // PCM audio format code = 1
            throw new RuntimeException("Unsupported format, WAV files must be PCM");

        // Num Channels
        channels = read() & 0xFF | (read() & 0xFF) << 8;
        if(channels != 1 && channels != 2)
            throw new RuntimeException("WAV files must have 1 or 2 channels: " + channels);

        // Sample Rate
        sampleRate = (read() & 0xFF | (read() & 0xFF) << 8 | (read() & 0xFF) << 16 | (read() & 0xFF) << 24);

        skipFully(6);

        // Bits Per Sample
        int bitsPerSample = read() & 0xFF | (read() & 0xFF) << 8;
        if(bitsPerSample != 16)
            throw new RuntimeException("WAV files must have 16 bits per sample: " + bitsPerSample);

        // Skip to Data Chunk
        skipFully(fmtChunkLength - 16);

        availableBytes = seekToChunk('d', 'a', 't', 'a');
    }

    public WavInputStream(File path) throws Exception{
        this(new FileInputStream(path));
    }

    public WavInputStream(String path) throws Exception{
        this(new File(path));
    }


    public int read(byte[] buffer) throws IOException{
        if(availableBytes == 0)
            return -1;

        int offset = 0;
        do{
            int length = Math.min(super.read(buffer, offset, buffer.length - offset), availableBytes);
            if(length == -1){
                if(offset > 0)
                    return offset;
                return -1;
            }
            offset += length;
            availableBytes -= length;
        }
        while(offset < buffer.length);

        return offset;
    }

    private void skipFully(int count) throws IOException{
        while(count > 0){
            long skipped = super.skip(count);
            if(skipped <= 0)
                throw new EOFException("Unable to skip");

            count -= skipped;
        }
    }

    private int seekToChunk(char c1, char c2, char c3, char c4) throws IOException{
        while(true){
            boolean found = read() == c1;
            found &= read() == c2;
            found &= read() == c3;
            found &= read() == c4;

            int chunkLength = read() & 0xFF | (read() & 0xFF) << 8 | (read() & 0xFF) << 16 | (read() & 0xFF) << 24;
            if(chunkLength == -1)
                throw new IOException("Chunk not found: " + c1 + c2 + c3 + c4);

            if(found)
                return chunkLength;

            skipFully(chunkLength);
        }
    }

    public int channels(){
        return channels;
    }

    public int sampleRate(){
        return sampleRate;
    }

    public int available(){
        return availableBytes;
    }

}