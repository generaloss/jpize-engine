package jpize.graphics.buffer;

import jpize.gl.buffer.GlBufUsage;
import jpize.gl.buffer.GlVertexBuffer;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;

import java.nio.*;

public class VertexBuffer extends GlVertexBuffer{

    private int vertexSize, vertexBytes;
    private GlBufUsage defaultUsage;

    public VertexBuffer(){
        bind();
        this.defaultUsage = GlBufUsage.STATIC_DRAW;
    }


    public void enableAttributes(GlVertexAttr... attributes){
        for(GlVertexAttr attribute: attributes){
            vertexSize += attribute.getCount();
            vertexBytes += attribute.getCount() * attribute.getType().getSize();
        }

        int pointer = 0;
        for(byte i = 0; i < attributes.length; i++){
            final GlVertexAttr attribute = attributes[i];
            
            final int count = attribute.getCount();
            final GlType type = attribute.getType();
            
            super.vertexAttribPointer(i, count, type, attribute.isNormalize(), vertexSize * type.getSize(), pointer);
            super.enableVertexAttribArray(i);

            pointer += count * type.getSize();
        }
    }

    public int getVertexSize(){
        return vertexSize;
    }
    
    public int getVertexBytes(){
        return vertexBytes;
    }

    public int getVertexCount(){
        return getSize() / vertexSize;
    }


    public void setDefaultUsage(GlBufUsage defaultUsage){
        this.defaultUsage = defaultUsage;
    }


    public void allocateData(long size){
        super.allocateData(size, defaultUsage);
    }

    public void allocateData(int size){
        super.allocateData(size, defaultUsage);
    }

    public void setData(float[] data){
        setData(data, defaultUsage);
    }

    public void setData(double[] data){
        setData(data, defaultUsage);
    }

    public void setData(int[] data){
        setData(data, defaultUsage);
    }

    public void setData(short[] data){
        setData(data, defaultUsage);
    }

    public void setData(IntBuffer data){
        setData(data, defaultUsage);
    }

    public void setData(ByteBuffer data){
        setData(data, defaultUsage);
    }

    public void setData(FloatBuffer data){
        setData(data, defaultUsage);
    }

    public void setData(ShortBuffer data){
        setData(data, defaultUsage);
    }

    public void setData(DoubleBuffer data){
        setData(data, defaultUsage);
    }

}