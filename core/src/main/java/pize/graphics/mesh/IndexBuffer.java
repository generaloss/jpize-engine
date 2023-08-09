package pize.graphics.mesh;

import pize.graphics.gl.BufUsage;
import pize.graphics.gl.GlObject;
import pize.graphics.gl.Type;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class IndexBuffer extends GlObject{
    
    private long dataSize;
    private Type type;
    
    public IndexBuffer(){
        super(glGenBuffers());
    }
    
    
    public int getIndicesNum(){
        return (int) dataSize;
    }


    public void setData(long size, BufUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, usage.GL);
        dataSize = size;
    }

    public void setData(int[] data, BufUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
        type = Type.UNSIGNED_INT;
    }

    public void setData(short[] data, BufUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.length;
        type = Type.UNSIGNED_SHORT;
    }

    public void setData(ByteBuffer data, BufUsage usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.GL);
        dataSize = data.limit();
        type = Type.UNSIGNED_BYTE;
    }


    public void setData(long size){
        setData(size, BufUsage.STATIC_DRAW);
    }

    public void setData(int[] data){
        setData(data, BufUsage.STATIC_DRAW);
    }

    public void setData(short[] data){
        setData(data, BufUsage.STATIC_DRAW);
    }

    public void setData(ByteBuffer data){
        setData(data, BufUsage.STATIC_DRAW);
    }


    public void setSubData(long offset, int[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
        dataSize = data.length;
        type = Type.UNSIGNED_INT;
    }

    public void setSubData(long offset, short[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
        dataSize = data.length;
        type = Type.UNSIGNED_SHORT;
    }


    public Type getIndicesType(){
        return type;
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