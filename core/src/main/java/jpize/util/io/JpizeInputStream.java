package jpize.util.io;

import jpize.graphics.util.color.Color;
import jpize.graphics.util.color.ImmutableColor;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class JpizeInputStream extends DataInputStream{
    
    public JpizeInputStream(InputStream in){
        super(in);
    }
    
    public short[] readShortArray() throws IOException{
        final byte[] bytes = readByteArray();
        
        final short[] shortArray = new short[bytes.length / 2];
        for(int i = 0; i < shortArray.length; i++)
            shortArray[i] = (short) (bytes[i * 2] & 0xFF << 8 | (bytes[i * 2 + 1] & 0xFF));
        
        return shortArray;
    }
    
    public byte[] readByteArray() throws IOException{
        return readNBytes(readInt());
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
    
}
