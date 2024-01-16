package jpize.util.io;

import jpize.util.color.Color;
import jpize.util.color.ImmutableColor;
import jpize.util.math.util.EulerAngles;
import jpize.util.math.vecmath.vector.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class JpizeInputStream extends DataInputStream{
    
    public JpizeInputStream(InputStream in){
        super(in);
    }
    
    public byte[] readByteArray() throws IOException{
        return readNBytes(readInt());
    }

    public short[] readShortArray() throws IOException{
        final byte[] bytes = readByteArray();

        final short[] shortArray = new short[bytes.length / 2];
        for(int i = 0; i < shortArray.length; i++){
            int index = i * 2;

            shortArray[i] = (short) (
                (bytes[index++] & 0xFF) << 8 |
                (bytes[index  ] & 0xFF)
            );
        }

        return shortArray;
    }

    public int[] readIntArray() throws IOException{
        final byte[] bytes = readByteArray();

        final int[] intArray = new int[bytes.length / 4];
        for(int i = 0; i < intArray.length; i++){
            int index = i * 4;

            intArray[i] = (
                (bytes[index++] & 0xFF) << 24 |
                (bytes[index++] & 0xFF) << 16 |
                (bytes[index++] & 0xFF) << 8  |
                (bytes[index  ] & 0xFF)
            );
        }

        return intArray;
    }

    public long[] readLongArray() throws IOException{
        final byte[] bytes = readByteArray();

        final long[] longArray = new long[bytes.length / 8];
        for(int i = 0; i < longArray.length; i++){
            int index = i * 8;

            longArray[i] = (
                ((long) bytes[index++] & 0xFF) << 56 |
                ((long) bytes[index++] & 0xFF) << 48 |
                ((long) bytes[index++] & 0xFF) << 40 |
                ((long) bytes[index++] & 0xFF) << 32 |
                ((long) bytes[index++] & 0xFF) << 24 |
                (       bytes[index++] & 0xFF) << 16 |
                (       bytes[index++] & 0xFF) << 8  |
                (       bytes[index  ] & 0xFF)
            );
        }

        return longArray;
    }


    public Vec2i readVec2i() throws IOException{
        return new Vec2i(
            readInt(),
            readInt()
        );
    }

    public Vec2f readVec2f() throws IOException{
        return new Vec2f(
            readFloat(),
            readFloat()
        );
    }

    public Vec2d readVec2d() throws IOException{
        return new Vec2d(
            readDouble(),
            readDouble()
        );
    }
    
    public Vec3i readVec3i() throws IOException{
        return new Vec3i(
            readInt(),
            readInt(),
            readInt()
        );
    }
    
    public Vec3f readVec3f() throws IOException{
        return new Vec3f(
            readFloat(),
            readFloat(),
            readFloat()
        );
    }

    public Vec3d readVec3d() throws IOException{
        return new Vec3d(
            readDouble(),
            readDouble(),
            readDouble()
        );
    }


    public EulerAngles readEulerAngles() throws IOException{
        return new EulerAngles(
            readFloat(),
            readFloat(),
            readFloat()
        );
    }
    
    public UUID readUUID() throws IOException{
        return new UUID(readLong(), readLong());
    }
    
    public Color readColor() throws IOException{
        return new Color(
            readFloat(),
            readFloat(),
            readFloat(),
            readFloat()
        );
    }

    public ImmutableColor readImmutableColor() throws IOException{
        return new ImmutableColor(
            readFloat(),
            readFloat(),
            readFloat(),
            readFloat()
        );
    }

    public ByteBuffer readByteBuffer() throws IOException{
        final byte[] array = readByteArray();
        final ByteBuffer buffer = ByteBuffer.allocateDirect(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }
    
}
