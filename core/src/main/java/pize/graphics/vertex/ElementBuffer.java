package pize.graphics.vertex;

import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.GlObject;

import static org.lwjgl.opengl.GL33.*;

public class ElementBuffer extends GlObject{
    
    private long dataSize;
    
    public ElementBuffer(){
        super(glGenBuffers());
        bind();
    }
    
    
    public int getIndicesNum(){
        return (int) dataSize;
    }


    public void setData(long size, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, usage.GL);
        dataSize = size;
    }

    public void setData(int[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }

    public void setData(short[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }

    public void setData(long[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
    }

    public void setSubData(long offset, int[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
        dataSize = data.length;
    }

    public void setSubData(long offset, short[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
        dataSize = data.length;
    }

    public void setSubData(long offset, long[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
        dataSize = data.length;
    }


    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ID);
    }

    public static void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }


    @Override
    public void dispose(){
        glDeleteBuffers(ID);
    }

}