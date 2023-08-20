package pize.lib.gl.buffer;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class GlBufferMapping{

    private final GlBuffer glBuffer;
    private final ByteBuffer mapBuffer;

    public GlBufferMapping(GlBuffer glBuffer, ByteBuffer mapBuffer){
        this.glBuffer = glBuffer;
        this.mapBuffer = mapBuffer;
    }


    public void write(ByteBuffer data, int writeOffset){
        final long scrAddress = MemoryUtil.memAddress(data);
        final long dstAddress = MemoryUtil.memAddress(mapBuffer, writeOffset);
        MemoryUtil.memCopy(scrAddress, dstAddress, data.remaining());
    }

    public void unmap(){
        glBuffer.unmap();
    }


    public GlBuffer getGlBuffer(){
        return glBuffer;
    }

    public ByteBuffer getMemoryBuffer(){
        return mapBuffer;
    }

}
