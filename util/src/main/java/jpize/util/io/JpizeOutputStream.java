package jpize.util.io;

import jpize.util.color.IColor;
import jpize.util.math.util.EulerAngles;
import jpize.util.math.vecmath.vector.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class JpizeOutputStream extends DataOutputStream{
    
    public JpizeOutputStream(OutputStream out){
        super(out);
    }
    
    public void writeByteArray(byte[] byteArray) throws IOException{
        writeInt(byteArray.length);
        write(byteArray);
    }

    public void writeShortArray(short[] shortArray) throws IOException{
        final byte[] bytes = new byte[shortArray.length * 2];
        for(int i = 0; i < shortArray.length; i++){
            final short j = shortArray[i];

            int index = i * 2;
            bytes[index++] = (byte) (j >> 8 & 0xFF);
            bytes[index  ] = (byte) (j & 0xFF);
        }

        writeByteArray(bytes);
    }

    public void writeIntArray(int[] intArray) throws IOException{
        final byte[] bytes = new byte[intArray.length * 4];
        for(int i = 0; i < intArray.length; i++){
            final int j = intArray[i];

            int index = i * 4;
            bytes[index++] = (byte) (j >> 24 & 0xFF);
            bytes[index++] = (byte) (j >> 16 & 0xFF);
            bytes[index++] = (byte) (j >> 8  & 0xFF);
            bytes[index  ] = (byte) (j       & 0xFF);
        }

        writeByteArray(bytes);
    }

    public void writeLongArray(long[] longArray) throws IOException{
        final byte[] bytes = new byte[longArray.length * 8];
        for(int i = 0; i < longArray.length; i++){
            final long j = longArray[i];

            int index = i * 8;
            bytes[index++] = (byte) (j >> 56 & 0xFF);
            bytes[index++] = (byte) (j >> 48 & 0xFF);
            bytes[index++] = (byte) (j >> 40 & 0xFF);
            bytes[index++] = (byte) (j >> 32 & 0xFF);
            bytes[index++] = (byte) (j >> 24 & 0xFF);
            bytes[index++] = (byte) (j >> 16 & 0xFF);
            bytes[index++] = (byte) (j >> 8  & 0xFF);
            bytes[index  ] = (byte) (j       & 0xFF);
        }

        writeByteArray(bytes);
    }


    public void writeVec2i(Vec2i vector) throws IOException{
        writeInt(vector.x);
        writeInt(vector.y);
    }

    public void writeVec2f(Vec2f vector) throws IOException{
        writeFloat(vector.x);
        writeFloat(vector.y);
    }

    public void writeVec2d(Vec2d vector) throws IOException{
        writeDouble(vector.x);
        writeDouble(vector.y);
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

    public void writeByteBuffer(ByteBuffer buffer) throws IOException{
        final byte[] array = new byte[buffer.remaining()];
        buffer.get(array);
        writeByteArray(array);
    }
    
}
