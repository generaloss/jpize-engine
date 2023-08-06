package pize.graphics.mesh;

import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.GlObject;
import pize.graphics.gl.Type;

import java.nio.*;

import static org.lwjgl.opengl.GL33.*;

public class VertexBuffer extends GlObject{

    private int vertexSizeCount, vertexSizeBytes;
    private long dataSizeCount;

    public VertexBuffer(){
        super(glGenBuffers());
        bind();
    }


    public void enableAttributes(VertexAttr... attributes){
        for(VertexAttr attribute: attributes){
            vertexSizeCount += attribute.getCount();
            vertexSizeBytes += attribute.getCount() * attribute.getType().getSize();
        }

        int pointer = 0;
        for(byte i = 0; i < attributes.length; i++){
            final VertexAttr attribute = attributes[i];
            
            final int count = attribute.getCount();
            final Type type = attribute.getType();
            
            glVertexAttribPointer(i, count, type.GL, attribute.isNormalize(), vertexSizeCount * type.getSize(), pointer);
            glEnableVertexAttribArray(i);

            pointer += count * type.getSize();
        }
    }

    public int getVertexSize(){
        return vertexSizeCount;
    }
    
    public int getVertexSizeBytes(){
        return vertexSizeBytes;
    }

    public long getDataSize(){
        return dataSizeCount;
    }

    public int getVerticesNum(){
        return (int) (dataSizeCount / vertexSizeCount);
    }


    public void setData(long size, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, size, usage.GL);
        dataSizeCount = size;
    }

    public void setData(float[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSizeCount = data.length;
    }

    public void setData(double[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSizeCount = data.length;
    }

    public void setData(int[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSizeCount = data.length;
    }

    public void setData(short[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSizeCount = data.length;
    }

    public void setData(long[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSizeCount = data.length;
    }


    public void setData(IntBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSizeCount = buffer.limit();
    }

    public void setData(ByteBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSizeCount = buffer.limit();
    }

    public void setData(FloatBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSizeCount = buffer.limit();
    }

    public void setData(LongBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSizeCount = buffer.limit();
    }

    public void setData(ShortBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSizeCount = buffer.limit();
    }

    public void setData(DoubleBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSizeCount = buffer.limit();
    }


    public void setSubData(long offset, float[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSizeCount = Math.max(dataSizeCount, offset + data.length);
    }

    public void setSubData(long offset, double[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSizeCount = Math.max(dataSizeCount, offset + data.length);
    }

    public void setSubData(long offset, int[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSizeCount = Math.max(dataSizeCount, offset + data.length);
    }

    public void setSubData(long offset, short[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSizeCount = Math.max(dataSizeCount, offset + data.length);
    }

    public void setSubData(long offset, long[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSizeCount = Math.max(dataSizeCount, offset + data.length);
    }


    public void bind(){
        glBindBuffer(GL_ARRAY_BUFFER, ID);
    }

    public static void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }


    @Override
    public void dispose(){
        glDeleteBuffers(ID);
    }

}