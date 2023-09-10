package jpize.gl.buffer;

import jpize.gl.GlObject;
import jpize.gl.texture.GlSizedFormat;
import jpize.gl.type.GlType;

import java.nio.*;

import static org.lwjgl.opengl.GL43.*;

public class GlBuffer extends GlObject{

    protected final GlBufferTarget target;

    public GlBuffer(GlBufferTarget target){
        super(glGenBuffers());
        this.target = target;
    }


    private GlBufferMapping createMapping(ByteBuffer mapBuffer){
        return new GlBufferMapping(this, mapBuffer);
    }

    public GlBufferMapping mapRange(long offset, long length, GlBufferAccess access){
        bind();
        final ByteBuffer mapBuffer = glMapBufferRange(target.GL, offset, length, access.GL);
        return createMapping(mapBuffer);
    }

    public GlBufferMapping map(GlBufferAccess access){
        bind();
        final ByteBuffer mapBuffer = glMapBuffer(target.GL, access.GL);
        return createMapping(mapBuffer);
    }

    public void unmap(){
        bind();
        glUnmapBuffer(target.GL);
    }


    public int getParameter(GlBufferParameter parameterName){
        bind();
        return glGetBufferParameteri(target.GL, parameterName.GL);
    }

    public void getParameter(GlBufferParameter parameterName, int[] params){
        bind();
        glGetBufferParameteriv(target.GL, parameterName.GL, params);
    }

    public void getParameter(GlBufferParameter parameterName, IntBuffer params){
        bind();
        glGetBufferParameteriv(target.GL, parameterName.GL, params);
    }


    public int getSize(){
        return getParameter(GlBufferParameter.BUFFER_SIZE);
    }

    public void copySubData(GlBuffer buffer, long readOffset, long writeOffset, long size){
        glBindBuffer(GL_COPY_READ_BUFFER, ID);
        glBindBuffer(GL_COPY_WRITE_BUFFER, buffer.ID);
        glCopyBufferSubData(GL_COPY_READ_BUFFER, GL_COPY_WRITE_BUFFER, readOffset, writeOffset, size);
    }


    public void allocateData(long bytes, GlBufUsage usage){
        bind();
        glBufferData(target.GL, bytes, usage.GL);
    }

    public void allocateData(int bytes, GlBufUsage usage){
        bind();
        glBufferData(target.GL, bytes, usage.GL);
    }

    public void setData(int[] data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(long[] data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(float[] data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(short[] data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(double[] data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(IntBuffer data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(ByteBuffer data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(LongBuffer data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(FloatBuffer data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(ShortBuffer data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }

    public void setData(DoubleBuffer data, GlBufUsage usage){
        bind();
        glBufferData(target.GL, data, usage.GL);
    }


    public void setSubData(long offsetBytes, int[] data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, long[] data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, float[] data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, short[] data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, double[] data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, IntBuffer data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, ByteBuffer data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, LongBuffer data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, FloatBuffer data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, ShortBuffer data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }

    public void setSubData(long offsetBytes, DoubleBuffer data){
        bind();
        glBufferSubData(target.GL, offsetBytes, data);
    }


    public void getSubData(long offsetBytes, int[] data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, long[] data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, float[] data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, short[] data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, double[] data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, IntBuffer data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, ByteBuffer data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, LongBuffer data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, FloatBuffer data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, ShortBuffer data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }

    public void getSubData(long offsetBytes, DoubleBuffer data){
        bind();
        glGetBufferSubData(target.GL, offsetBytes, data);
    }


    public void clearData(GlSizedFormat format, GlType type, int[] data){
        bind();
        glClearBufferData(target.GL, format.GL, format.getBase().GL, type.GL, data);
    }

    public void clearData(GlSizedFormat format, GlType type, float[] data){
        bind();
        glClearBufferData(target.GL, format.GL, format.getBase().GL, type.GL, data);
    }

    public void clearData(GlSizedFormat format, GlType type, short[] data){
        bind();
        glClearBufferData(target.GL, format.GL, format.getBase().GL, type.GL, data);
    }

    public void clearData(GlSizedFormat format, GlType type, IntBuffer data){
        bind();
        glClearBufferData(target.GL, format.GL, format.getBase().GL, type.GL, data);
    }

    public void clearData(GlSizedFormat format, GlType type, ByteBuffer data){
        bind();
        glClearBufferData(target.GL, format.GL, format.getBase().GL, type.GL, data);
    }

    public void clearData(GlSizedFormat format, GlType type, FloatBuffer data){
        bind();
        glClearBufferData(target.GL, format.GL, format.getBase().GL, type.GL, data);
    }

    public void clearData(GlSizedFormat format, GlType type, ShortBuffer data){
        bind();
        glClearBufferData(target.GL, format.GL, format.getBase().GL, type.GL, data);
    }


    public void clearSubData(GlSizedFormat format, GlType type, long offset, long size, int[] data){
        bind();
        glClearBufferSubData(target.GL, format.GL, offset, size, format.getBase().GL, type.GL, data);
    }

    public void clearSubData(GlSizedFormat format, GlType type, long offset, long size, float[] data){
        bind();
        glClearBufferSubData(target.GL, format.GL, offset, size, format.getBase().GL, type.GL, data);
    }

    public void clearSubData(GlSizedFormat format, GlType type, long offset, long size, short[] data){
        bind();
        glClearBufferSubData(target.GL, format.GL, offset, size, format.getBase().GL, type.GL, data);
    }

    public void clearSubData(GlSizedFormat format, GlType type, long offset, long size, IntBuffer data){
        bind();
        glClearBufferSubData(target.GL, format.GL, offset, size, format.getBase().GL, type.GL, data);
    }

    public void clearSubData(GlSizedFormat format, GlType type, long offset, long size, ByteBuffer data){
        bind();
        glClearBufferSubData(target.GL, format.GL, offset, size, format.getBase().GL, type.GL, data);
    }

    public void clearSubData(GlSizedFormat format, GlType type, long offset, long size, FloatBuffer data){
        bind();
        glClearBufferSubData(target.GL, format.GL, offset, size, format.getBase().GL, type.GL, data);
    }

    public void clearSubData(GlSizedFormat format, GlType type, long offset, long size, ShortBuffer data){
        bind();
        glClearBufferSubData(target.GL, format.GL, offset, size, format.getBase().GL, type.GL, data);
    }


    public void bind(){
        glBindBuffer(target.GL, ID);
    }

    public void unbind(){
        glBindBuffer(target.GL, 0);
    }

    public static void unbindAll(){
        for(GlBufferTarget target: GlBufferTarget.values())
            glBindBuffer(target.GL, 0);
    }

    @Override
    public void dispose(){
        glDeleteBuffers(ID);
    }

}
