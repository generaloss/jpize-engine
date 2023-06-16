package pize.net.tcp.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.tuple.Tuple3d;
import pize.math.vecmath.tuple.Tuple3f;
import pize.math.vecmath.vector.Vec3d;
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
    
    public Tuple3f readTuple3f() throws IOException{
        return new Vec3f(
            readFloat(),
            readFloat(),
            readFloat()
        );
    }

    public Tuple3d readTuple3d() throws IOException{
        return new Vec3d(
            readDouble(),
            readDouble(),
            readDouble()
        );
    }

    public EulerAngles readEulerAngles() throws IOException{
        return new EulerAngles(
            readDouble(),
            readDouble(),
            readDouble()
        );
    }
    
}
