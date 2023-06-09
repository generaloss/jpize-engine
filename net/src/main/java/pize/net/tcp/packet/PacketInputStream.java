package pize.net.tcp.packet;

import pize.math.vecmath.vector.Vec3f;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketInputStream extends DataInputStream{
    
    public PacketInputStream(InputStream in){
        super(in);
    }
    
    public short[] readShortArray() throws IOException{
        final byte[] bytes = readByteArray();
        
        final short[] shortArray = new short[bytes.length / 2];
        for(int i = 0; i < shortArray.length; i++){
            shortArray[i] = (short) (bytes[i * 2] & 0xFF << 8 | (bytes[i * 2 + 1] & 0xFF));
        }
        
        return shortArray;
    }
    
    public byte[] readByteArray() throws IOException{
        return readNBytes(readInt());
    }
    
    public Vec3f readVec3f() throws IOException{
        return new Vec3f(
            readFloat(),
            readFloat(),
            readFloat()
        );
    }
    
}
