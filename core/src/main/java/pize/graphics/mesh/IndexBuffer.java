package pize.graphics.mesh;

import pize.lib.gl.buffer.GlBufUsage;
import pize.lib.gl.buffer.GlIndexBuffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class IndexBuffer extends GlIndexBuffer{

    private GlBufUsage defaultUsage;
    
    public IndexBuffer(){
        this.defaultUsage = GlBufUsage.STATIC_DRAW;
    }


    public void setDefaultUsage(GlBufUsage defaultUsage){
        this.defaultUsage = defaultUsage;
    }


    public void setData(int[] data){
        setData(data, defaultUsage);
    }

    public void setData(long size){
        allocateData(size, defaultUsage);
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

    public void setData(LongBuffer data){
        setData(data, defaultUsage);
    }

    public void setData(ShortBuffer data){
        setData(data, defaultUsage);
    }

}