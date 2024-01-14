package jpize.graphics.texture.pixmap;

import jpize.gl.texture.GlSizedFormat;
import jpize.util.math.Maths;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class PixmapA extends Pixmap{

    public PixmapA(ByteBuffer buffer, int width, int height){
        super(buffer, width, height, 1);
    }

    public PixmapA(PixmapA pixmap){
        this(pixmap.buffer, pixmap.width, pixmap.height);
    }

    public PixmapA(int width, int height){
        super(width, height, 1);
    }


    public float getPixelValue(int x, int y){
        return (buffer.get(getIndex(x, y)) & 0xFF) / 255F;
    }

    public void setPixel(int x, int y, double value){
        if(!outOfBounds(x, y))
            buffer.put(getIndex(x, y), Maths.toByteRange(value));
    }

    public void setPixel(int x, int y, int value){
        if(!outOfBounds(x, y))
            buffer.put(getIndex(x, y), (byte) (value & 0xFF));
    }


    public PixmapRGBA toPixmapRGBA(float r, float g, float b){
        final PixmapRGBA pixmap = new PixmapRGBA(width, height);

        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++){
                final float a = getPixelValue(x, y);
                pixmap.setPixel(x, y, r, g, b, a);
            }

        return pixmap;
    }

    public PixmapRGBA toPixmapRGBA(){
        return toPixmapRGBA(1F, 1F, 1F);
    }


    @Override
    public void resize(int width, int height){
        if(super.matchSize(width, height))
            return;

        setSize(width, height);
        buffer.clear();
        // Utils.free(buffer);
        buffer = BufferUtils.createByteBuffer(width * height);
    }

    @Override
    public GlSizedFormat getFormat(){
        return GlSizedFormat.ALPHA8;
    }

    @Override
    public Pixmap copy(){
        return new PixmapA(this);
    }

}
