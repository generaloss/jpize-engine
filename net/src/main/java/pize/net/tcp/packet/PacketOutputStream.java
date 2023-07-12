package pize.net.tcp.packet;

import pize.graphics.util.color.IColor;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;
import pize.math.vecmath.vector.Vec3i;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

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
    
    public void writeVec3i(Vec3i vector) throws IOException{
        writeInt(vector.x);
        writeInt(vector.y);
        writeInt(vector.z);
    }
    
    public void writeVec3f(Vec3f vector) throws IOException{
        writeFloat(vector.x);
        writeFloat(vector.y);
        writeFloat(vector.z);
    }

    public void writeVec3d(Vec3d vector) throws IOException{
        writeDouble(vector.x);
        writeDouble(vector.y);
        writeDouble(vector.z);
    }

    public void writeEulerAngles(EulerAngles vector) throws IOException{
        writeFloat(vector.yaw);
        writeFloat(vector.pitch);
        writeFloat(vector.roll);
    }
    
    public void writeUUID(UUID uuid) throws IOException{
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }
    
    public void writeColor(IColor color) throws IOException{
        writeFloat(color.r());
        writeFloat(color.g());
        writeFloat(color.b());
        writeFloat(color.a());
    }
    
}
