package pize.graphics.vertex;

import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.GlObject;
import pize.graphics.gl.Type;

import java.nio.*;

import static org.lwjgl.opengl.GL33.*;

public class VertexBuffer extends GlObject{

    private int vertexSize, vertexBytes;
    private long dataSize;

    public VertexBuffer(){
        super(glGenBuffers());
        bind();
    }


    public void enableAttributes(VertexAttr... attributes){
        for(VertexAttr attribute: attributes){
            vertexSize += attribute.getCount();
            vertexBytes += attribute.getCount() * attribute.getType().getSize();
        }

        int pointer = 0;
        for(byte i = 0; i < attributes.length; i++){
            final VertexAttr attribute = attributes[i];
            
            final int count = attribute.getCount();
            final Type type = attribute.getType();
            
            glVertexAttribPointer(i, count, type.GL, attribute.isNormalize(), vertexSize * type.getSize(), pointer);
            glEnableVertexAttribArray(i);

            pointer += count * type.getSize();
        }
    }

    public int getVertexSize(){
        return vertexSize;
    }
    
    public int getVertexBytes(){
        return vertexBytes;
    }

    public long getDataSize(){
        return dataSize;
    }

    public int getVerticesNum(){
        return (int) (dataSize / vertexSize);
    }


    public void setData(long size, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, size, usage.GL);
        dataSize = size;
    }

    public void setData(float[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }

    public void setData(double[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }

    public void setData(int[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }

    public void setData(short[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }

    public void setData(long[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }


    public void setData(IntBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSize = buffer.limit();
    }

    public void setData(ByteBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSize = buffer.limit();
    }

    public void setData(FloatBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSize = buffer.limit();
    }

    public void setData(LongBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSize = buffer.limit();
    }

    public void setData(ShortBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSize = buffer.limit();
    }

    public void setData(DoubleBuffer buffer, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, usage.GL);
        dataSize = buffer.limit();
    }


    public void setSubData(long offset, float[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSize = Math.max(dataSize, offset + data.length);
    }

    public void setSubData(long offset, double[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSize = Math.max(dataSize, offset + data.length);
    }

    public void setSubData(long offset, int[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSize = Math.max(dataSize, offset + data.length);
    }

    public void setSubData(long offset, short[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSize = Math.max(dataSize, offset + data.length);
    }

    public void setSubData(long offset, long[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, offset, data);
        dataSize = Math.max(dataSize, offset + data.length);
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