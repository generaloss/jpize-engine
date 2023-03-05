package glit.graphics.vertex;

import glit.context.Disposable;
import glit.graphics.gl.BufferUsage;

import static org.lwjgl.opengl.GL33.*;

public class ElementBuffer implements Disposable{

    private final int id;

    public ElementBuffer(){
        id = glGenBuffers();
        bind();
    }


    public void setData(long size, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, usage.gl);
    }

    public void setData(float[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl);
    }

    public void setData(double[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl);
    }

    public void setData(int[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl);
    }

    public void setData(short[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl);
    }

    public void setData(long[] data, BufferUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl);
    }

    public void setSubData(long offset, float[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
    }

    public void setSubData(long offset, double[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
    }

    public void setSubData(long offset, int[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
    }

    public void setSubData(long offset, short[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
    }

    public void setSubData(long offset, long[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
    }


    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public static void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getId(){
        return id;
    }


    @Override
    public void dispose(){
        glDeleteBuffers(id);
    }

}