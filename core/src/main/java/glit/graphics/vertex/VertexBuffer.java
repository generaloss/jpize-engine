package glit.graphics.vertex;

import glit.context.Disposable;
import glit.graphics.gl.BufferUsage;

import static org.lwjgl.opengl.GL33.*;

public class VertexBuffer implements Disposable{

    private final int id;
    private int vertexSize;
    private long dataSize;


    public VertexBuffer(){
        id = glGenBuffers();
        bind();
    }


    public void enableAttributes(VertexAttr... attributes){
        for(VertexAttr attribute: attributes)
            vertexSize += attribute.getCount();

        int pointer = 0;
        for(byte i = 0; i < attributes.length; i++){
            VertexAttr attribute = attributes[i];
            int typeSize = attribute.getType().getSize();

            glVertexAttribPointer(i, attribute.getCount(), attribute.getType().gl, attribute.isNormalize(), vertexSize * typeSize, pointer);
            glEnableVertexAttribArray(i);

            pointer += attribute.getCount() * typeSize;
        }
    }

    public int getVertexSize(){
        return vertexSize;
    }

    public long getDataSize(){
        return dataSize;
    }

    public int getVertexCount(){
        return (int) (dataSize / vertexSize);
    }


    public void setData(long size, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, size, usage.gl);
        dataSize = size;
    }

    public void setData(float[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl);
        dataSize = data.length;
    }

    public void setData(double[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl);
        dataSize = data.length;
    }

    public void setData(int[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl);
        dataSize = data.length;
    }

    public void setData(short[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl);
        dataSize = data.length;
    }

    public void setData(long[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl);
        dataSize = data.length;
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
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public static void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getId(){
        return id;
    }


    @Override
    public void dispose(){
        glDeleteBuffers(id);
    }

}