package pize.net.tcp.packet;

import pize.math.util.EulerAngles;
import pize.math.vecmath.tuple.Tuple3d;
import pize.math.vecmath.tuple.Tuple3f;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PacketOutputStream extends DataOutputStream{
    
    public PacketOutputStream(OutputStream out){
        super(out);
    }
    
    public void writeShortArray(short[] shortArray) throws IOException{
        final byte[] bytes = new byte[shortArray.length * 2];
        for(int i = 0; i < shortArray.length; i++){
            final short j = shortArray[i];
            bytes[i * 2] = (byte) (j >> 8 & 0xFF);
            bytes[i * 2 + 1] = (byte) (j & 0xFF);
        }
        
        writeByteArray(bytes);
    }
    
    public void writeByteArray(byte[] byteArray) throws IOException{
        writeInt(byteArray.length);
        write(byteArray);
    }
    
    public void writeTuple3f(Tuple3f vector) throws IOException{
        writeFloat(vector.x);
        writeFloat(vector.y);
        writeFloat(vector.z);
    }

    public void writeTuple3d(Tuple3d vector) throws IOException{
        writeDouble(vector.x);
        writeDouble(vector.y);
        writeDouble(vector.z);
    }

    public void writeEulerAngles(EulerAngles vector) throws IOException{
        writeDouble(vector.yaw);
        writeDouble(vector.pitch);
        writeDouble(vector.roll);
    }
    
}
