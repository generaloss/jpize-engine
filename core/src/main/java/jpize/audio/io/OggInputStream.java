package jpize.audio.io;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

/** @author kevin */
public class OggInputStream extends InputStream{

    private final static boolean BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    private final static int BUFFER_SIZE = 512;

    private final InputStream input;
    private final Info oggInfo;
    private final SyncState syncState;
    private final StreamState streamState;
    private final Page page;
    private final Packet packet;
    private final Comment comment;
    private final DspState dspState;
    private final Block vorbisBlock;

    private final byte[] convBuffer;
    private final byte[] outBuffer;

    private int convSize;
    private final int size;
    private boolean endOfStream;
    private int bytes;
    private boolean endOfBitStream;
    private boolean initialized;
    private int readIndex;
    private int outIndex;


    public OggInputStream(InputStream input, OggInputStream prevOggStream){
        this.input = input;
        this.endOfBitStream = true;
        this.convSize = BUFFER_SIZE * 4;

        this.oggInfo = new Info(); // struct that stores all the static vorbis bitstream settings
        this.syncState = new SyncState(); // sync and verify incoming physical bitstream
        this.streamState = new StreamState(); // take physical pages, weld into a logical stream of packets
        this.page = new Page(); // one Ogg bitstream page. Vorbis packets are inside
        this.packet = new Packet(); // one raw packet of data for decode
        this.comment = new Comment(); // struct that stores all the bitstream user comments
        this.dspState = new DspState(); // central working state for the packet->PCM decoder
        this.vorbisBlock = new Block(dspState); // local working space for packet->PCM decode

        if(prevOggStream == null){
            this.convBuffer = new byte[convSize];
            this.outBuffer = new byte[4096 * 500];
        }else{
            this.convBuffer = prevOggStream.convBuffer;
            this.outBuffer = prevOggStream.outBuffer;
        }

        try{
            this.size = input.available();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        readPCM();
    }

    public OggInputStream(InputStream input){
        this(input, null);
    }


    private void readPCM(){
        boolean wrote = false;

        while(true){ // we repeat if the bitstream is chained
            if(endOfBitStream){
                if(!getPageAndPacket())
                    break;

                endOfBitStream = false;
            }

            if(!initialized){
                initialized = true;
                return;
            }

            final float[][][] _pcm = new float[1][][];
            final int[] _index = new int[oggInfo.channels];

            // The rest is just a straight decode loop until end of stream
            while(!endOfBitStream){
                while(!endOfBitStream){

                    int result = syncState.pageout(page);
                    if(result == 0)
                        break; // need more data

                    if(result == -1){ // missing or corrupt data at this page position
                        throw new RuntimeException("Corrupt or missing Ogg data in bitstream");
                    }else{
                        streamState.pagein(page); // can safely ignore errors at
                        // this point
                        while(true){
                            result = streamState.packetout(packet);

                            if(result == 0)
                                break; // need more data
                            if(result == -1){ // missing or corrupt data at this page position
                                // no reason to complain; already complained above
                            }else{
                                // we have a packet. Decode it

                                // test for success!
                                if(vorbisBlock.synthesis(packet) == 0)
                                    dspState.synthesis_blockin(vorbisBlock);

                                // **pcm is a multichannel float vector. In stereo, for
                                // example, pcm[0] is left, and pcm[1] is right. samples is
                                // the size of each channel. Convert the float values
                                // (-1.<=range<=1.) to whatever PCM format and write it out

                                int samples;
                                while( (samples = dspState.synthesis_pcmout(_pcm, _index)) > 0 ){
                                    final float[][] pcm = _pcm[0];
                                    final int bout = Math.min(samples, convSize);
                                    // boolean clipflag = false;

                                    // convert floats to 16 bit signed ints (host order) and interleave
                                    for(int i = 0; i < oggInfo.channels; i++){
                                        int ptr = i * 2; // int ptr = i;
                                        final int mono = _index[i];

                                        for(int j = 0; j < bout; j++){
                                            int val = (int) (pcm[i][mono + j] * 32767);

                                            // might as well guard against clipping
                                            if(val > 32767)
                                                val = 32767;

                                            if(val < -32768)
                                                val = -32768;

                                            if(val < 0)
                                                val = val | 0x8000;

                                            if(BIG_ENDIAN){
                                                convBuffer[ptr] = (byte) (val >>> 8);
                                                convBuffer[ptr + 1] = (byte) (val);
                                            }else{
                                                convBuffer[ptr] = (byte) (val);
                                                convBuffer[ptr + 1] = (byte) (val >>> 8);
                                            }

                                            ptr += 2 * (oggInfo.channels);
                                        }
                                    }

                                    final int bytesToWrite = 2 * oggInfo.channels * bout;
                                    if(outIndex + bytesToWrite > outBuffer.length){
                                        throw new RuntimeException("Ogg block too big to be buffered: " + bytesToWrite + ", " + (outBuffer.length - outIndex));
                                    }else{
                                        System.arraycopy(convBuffer, 0, outBuffer, outIndex, bytesToWrite);
                                        outIndex += bytesToWrite;
                                    }

                                    wrote = true;
                                    dspState.synthesis_read(bout); // tell libvorbis how many samples we actually consumed
                                }
                            }
                        }

                        if(page.eos() != 0)
                            endOfBitStream = true;

                        if(!endOfBitStream && wrote)
                            return;
                    }
                }

                if(!endOfBitStream){
                    bytes = 0;
                    final int index = syncState.buffer(BUFFER_SIZE);
                    if(index >= 0){
                        try{
                            bytes = input.read(syncState.data, index, BUFFER_SIZE);
                        }catch(IOException e){
                            throw new RuntimeException("Error during Vorbis decoding", e);
                        }
                    }else{
                        bytes = 0;
                    }
                    syncState.wrote(bytes);
                    if(bytes == 0){
                        endOfBitStream = true;
                    }
                }
            }

            // clean up this logical bitstream; before exit we see if we're
            // followed by another [chained]
            streamState.clear();

            // ogg_page and ogg_packet structs always point to storage in
            // libvorbis. They're never freed or manipulated directly

            vorbisBlock.clear();
            dspState.clear();
            oggInfo.clear(); // must be called last
        }

        // OK, clean up the framer
        syncState.clear();
        endOfStream = true;
    }

    private boolean getPageAndPacket(){
        // grab some data at the head of the stream. We want the first page
        // (which is guaranteed to be small and only contain the Vorbis
        // stream initial header) We need the first page to get the stream
        // serialno.

        // submit a 4k block to 'libvorbis' ogg layer
        int index = syncState.buffer(BUFFER_SIZE);
        if(index == -1)
            return false;

        final byte[] buffer = syncState.data;
        if(buffer == null){
            endOfStream = true;
            return false;
        }

        try{
            bytes = input.read(buffer, index, BUFFER_SIZE);
        }catch(Exception e){
            throw new RuntimeException("Failure reading Vorbis", e);
        }
        syncState.wrote(bytes);

        // Get the first page.
        if(syncState.pageout(page) != 1){
            // have we simply run out of data? If so, we're done.
            if(bytes < BUFFER_SIZE)
                return false;

            // error case. Must not be Vorbis data
            throw new RuntimeException("Input does not appear to be an Ogg bitstream");
        }

        // Get the serial number and set up the rest of decode.
        // serialno first; use it to set up a logical stream
        streamState.init(page.serialno());

        // extract the initial header from the first page and verify that the
        // Ogg bitstream is in fact Vorbis data

        // I handle the initial header first instead of just having the code
        // read all three Vorbis headers at once because reading the initial
        // header is an easy way to identify a Vorbis bitstream and it's
        // useful to see that functionality seperated out.

        oggInfo.init();
        comment.init();

        // error; stream version mismatch perhaps
        if(streamState.pagein(page) < 0)
            throw new RuntimeException("Error reading first page of Ogg bitstream.");

        // no page? must not be vorbis
        if(streamState.packetout(packet) != 1)
            throw new RuntimeException("Error reading initial Ogg header packet");

        // error case; not a vorbis header
        if(oggInfo.synthesis_headerin(comment, packet) < 0)
            throw new RuntimeException("Ogg bitstream does not contain Vorbis audio data.");

        // At this point, we're sure we're Vorbis. We've set up the logical
        // (Ogg) bitstream decoder. Get the comment and codebook headers and
        // set up the Vorbis decoder

        // The next two packets in order are the comment and codebook headers.
        // They're likely large and may span multiple pages. Thus we reead
        // and submit data until we get our two pacakets, watching that no
        // pages are missing. If a page is missing, error out; losing a
        // header page is the only place where missing data is fatal. */

        int i = 0;
        while(i < 2){
            while(i < 2){
                int result = syncState.pageout(page);
                if(result == 0)
                    break; // Need more data
                // Don't complain about missing or corrupt data yet. We'll
                // catch it at the packet output phase

                if(result == 1){
                    streamState.pagein(page); // we can ignore any errors here
                    // as they'll also become apparent
                    // at packetout
                    while(i < 2){
                        result = streamState.packetout(packet);
                        if(result == 0)
                            break;
                        if(result == -1){
                            // Uh oh; data at some point was corrupted or missing!
                            // We can't tolerate that in a header. Die.
                            throw new RuntimeException("Corrupt secondary Ogg header");
                        }

                        oggInfo.synthesis_headerin(comment, packet);
                        i++;
                    }
                }
            }
            // no harm in not checking before adding more
            index = syncState.buffer(BUFFER_SIZE);
            if(index == -1)
                return false;

            try{
                bytes = input.read(syncState.data, index, BUFFER_SIZE);
            }catch(Exception e){
                throw new RuntimeException("Failed to read Vorbis", e);
            }
            if(bytes == 0 && i < 2){
                throw new RuntimeException("End of file before finding all Vorbis headers");
            }
            syncState.wrote(bytes);
        }

        convSize = BUFFER_SIZE / oggInfo.channels;

        // OK, got and parsed all three headers. Initialize the Vorbis
        // packet->PCM decoder.
        dspState.synthesis_init(oggInfo); // central decode state
        vorbisBlock.init(dspState); // local state for most of the decode
        // so multiple block decodes can
        // proceed in parallel. We could init
        // multiple vorbis_block structures
        // for vd here

        return true;
    }


    public int read(){
        if(readIndex >= outIndex){
            outIndex = 0;
            readPCM();
            readIndex = 0;
            if(outIndex == 0)
                return -1;
        }

        int value = outBuffer[readIndex];
        if(value < 0)
            value = 256 + value;

        readIndex++;
        return value;
    }

    public int read(byte @NotNull [] bytes, int offset, int length){
        for(int i = 0; i < length; i++){
            final int value = read();

            if(value >= 0)
                bytes[i] = (byte) value;
            else if(i == 0)
                return -1;
            else
                return i;
        }

        return length;
    }

    public int read(byte @NotNull [] bytes){
        return read(bytes, 0, bytes.length);
    }


    public boolean atEnd(){
        return endOfStream && (readIndex >= outIndex);
    }

    public int size(){
        return size;
    }

    public int channels(){
        return oggInfo.channels;
    }

    public int sampleRate(){
        return oggInfo.rate;
    }

    public int available(){
        return endOfStream ? 0 : 1;
    }

}