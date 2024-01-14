package jpize.graphics.texture.pixmap;

import jpize.gl.texture.GlSizedFormat;
import jpize.graphics.util.Sizable;
import jpize.app.Resizable;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public abstract class Pixmap extends Sizable implements Resizable{

    protected int channels;
    protected ByteBuffer buffer;

    public Pixmap(int width, int height, int channels){
        super(width, height);
        this.channels = channels;
        this.buffer = BufferUtils.createByteBuffer(width * height * channels);
    }

    public Pixmap(ByteBuffer buffer, int width, int height, int channels){
        super(width, height);
        this.channels = channels;
        this.buffer = buffer.duplicate();
    }

    public ByteBuffer getBuffer(){
        return buffer;
    }


    public void clear(){
        for(int i = 0; i < buffer.capacity(); i++)
            buffer.put(i, (byte) 0);
    }

    public void clearChannel(int channel, double value){
        for(int i = 0; i < buffer.capacity(); i += channels)
            buffer.put(i + channel, (byte) (value * 255));
    }

    public boolean outOfBounds(int x, int y){
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    public int getIndex(int x, int y, int channel){
        return (y * width + x) * channels + channel;
    }

    public int getIndex(int x, int y){
        return (y * width + x) * channels;
    }


    public void set(Pixmap pixmap){
        if(pixmap == null || pixmap.channels != channels)
            return;

        if(!pixmap.matchSize(this)){
            setSize(pixmap);
            buffer.clear();
            // Utils.free(buffer);
            buffer = BufferUtils.createByteBuffer(width * height * channels);
        }

        for(int i = 0; i < buffer.limit(); i++)
            buffer.put(i, pixmap.buffer.get(i));
    }


    public abstract GlSizedFormat getFormat();

    public abstract Pixmap copy();

}
