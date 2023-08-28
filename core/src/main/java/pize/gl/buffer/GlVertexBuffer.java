package pize.gl.buffer;

import pize.gl.type.GlType;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class GlVertexBuffer extends GlBuffer{

    public GlVertexBuffer(){
        super(GlBufferTarget.ARRAY_BUFFER);
    }


    public void vertexAttribPointer(int index, int size, GlType type, boolean normalized, int stride, long pointer){
        glVertexAttribPointer(index, size, type.GL, normalized, stride, pointer);
    }

    public void vertexAttribPointer(int index, int size, GlType type, boolean normalized, int stride, IntBuffer pointer){
        glVertexAttribPointer(index, size, type.GL, normalized, stride, pointer);
    }

    public void vertexAttribPointer(int index, int size, GlType type, boolean normalized, int stride, ByteBuffer pointer){
        glVertexAttribPointer(index, size, type.GL, normalized, stride, pointer);
    }

    public void vertexAttribPointer(int index, int size, GlType type, boolean normalized, int stride, FloatBuffer pointer){
        glVertexAttribPointer(index, size, type.GL, normalized, stride, pointer);
    }

    public void vertexAttribPointer(int index, int size, GlType type, boolean normalized, int stride, ShortBuffer pointer){
        glVertexAttribPointer(index, size, type.GL, normalized, stride, pointer);
    }


    public void enableVertexAttribArray(int index){
        glEnableVertexAttribArray(index);
    }

}
