package pize.graphics.texture;

import org.lwjgl.BufferUtils;
import pize.app.Resizable;
import pize.lib.gl.texture.GlSizedFormat;
import pize.graphics.util.Sizable;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2d;

import java.nio.ByteBuffer;

public class Pixmap extends Sizable implements Resizable{

    public static final GlSizedFormat FORMAT = GlSizedFormat.RGBA8;
    private ByteBuffer buffer;

    // CONSTRUCTOR
    public Pixmap(ByteBuffer buffer, int width, int height){
        super(width, height);
        this.buffer = buffer.duplicate();
    }

    public Pixmap(Pixmap pixmap){
        this(pixmap.buffer, pixmap.width, pixmap.height);
    }

    public Pixmap(int width, int height){
        super(width, height);
        buffer = BufferUtils.createByteBuffer(width * height * 4);
    }
    

    // SET
    public void set(Pixmap pixmap){
        if(pixmap == null)
            return;

        if(!pixmap.match(this)){
            setSize(pixmap);
            buffer.clear();
            // Utils.free(buffer);
            buffer = BufferUtils.createByteBuffer(width * height * 4);
        }
        for(int i = 0; i < buffer.limit(); i++)
            buffer.put(i, pixmap.buffer.get(i));
    }

    // SET PIXEL
    public void setPixel(int x, int y, int color){
        if(outOfBounds(x, y))
            return;

        final Color blendColor = blend(
            getPixelColor(x, y),
            new Color(color >> 24 & 0xFF, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF)
        );
        buffer.put(getIndex(x, y, 0), (byte) ((color >> 24) & 0xFF));
        buffer.put(getIndex(x, y, 1), (byte) ((color >> 16) & 0xFF));
        buffer.put(getIndex(x, y, 2), (byte) ((color >> 8) & 0xFF));
        buffer.put(getIndex(x, y, 3), (byte) ((color) & 0xFF));
    }

    public void setPixel(int x, int y, IColor color){
        if(outOfBounds(x, y))
            return;

        final Color blendColor = blend(getPixelColor(x, y), color);
        buffer.put(getIndex(x, y, 0), (byte) ((int) (blendColor.r() * 255) & 0xFF));
        buffer.put(getIndex(x, y, 1), (byte) ((int) (blendColor.g() * 255) & 0xFF));
        buffer.put(getIndex(x, y, 2), (byte) ((int) (blendColor.b() * 255) & 0xFF));
        buffer.put(getIndex(x, y, 3), (byte) ((int) (blendColor.a() * 255) & 0xFF));
    }

    public void setPixel(int x, int y, double r, double g, double b, double a){
        if(outOfBounds(x, y))
            return;

        Color blendColor = blend(getPixelColor(x, y), new Color(r, g, b, a));
        buffer.put(getIndex(x, y, 0), (byte) ((int) (blendColor.r() * 255) & 0xFF));
        buffer.put(getIndex(x, y, 1), (byte) ((int) (blendColor.g() * 255) & 0xFF));
        buffer.put(getIndex(x, y, 2), (byte) ((int) (blendColor.b() * 255) & 0xFF));
        buffer.put(getIndex(x, y, 3), (byte) ((int) (blendColor.a() * 255) & 0xFF));
    }

    public void setPixel(int x, int y, int r, int g, int b, int a){
        if(outOfBounds(x, y))
            return;

        Color blendColor = blend(getPixelColor(x, y), new Color(r, g, b, a));
        buffer.put(getIndex(x, y, 0), (byte) (r & 0xFF));
        buffer.put(getIndex(x, y, 1), (byte) (g & 0xFF));
        buffer.put(getIndex(x, y, 2), (byte) (b & 0xFF));
        buffer.put(getIndex(x, y, 3), (byte) (a & 0xFF));
    }

    // BLENDING
    private Color blend(Color color1, IColor color2){
        color1.set(
            color1.r() * (1 - color2.a()) + color2.r() * (color2.a()),
            color1.g() * (1 - color2.a()) + color2.g() * (color2.a()),
            color1.b() * (1 - color2.a()) + color2.b() * (color2.a()),
            Math.max(color1.a(), color2.a())
        );

        return color1;
    }

    private Color blend(Color color1, double r2, double g2, double b2, double a2){
        color1.set(
            color1.r() * (1 - a2) + r2 * (a2),
            color1.g() * (1 - a2) + g2 * (a2),
            color1.b() * (1 - a2) + b2 * (a2),
            Math.max(color1.a(), a2)
        );

        return color1;
    }

    private IColor blend(double r1, double g1, double b1, double a1, double r2, double g2, double b2, double a2){
        return new Color(
            r1 * (1 - a2) + r2 * (a2),
            g1 * (1 - a2) + g2 * (a2),
            b1 * (1 - a2) + b2 * (a2),
            Math.max(a1, a2)
        );
    }

    // GET PIXEL
    public int getPixelABGR(int x, int y){
        int r = buffer.get(getIndex(x, y, 0)) & 0xFF;
        int g = buffer.get(getIndex(x, y, 1)) & 0xFF;
        int b = buffer.get(getIndex(x, y, 2)) & 0xFF;
        int a = buffer.get(getIndex(x, y, 3)) & 0xFF;
        return r << 24 | g << 16 | b << 8 | a;
    }
    
    public int getPixelBGRA(int x, int y){
        int r = buffer.get(getIndex(x, y, 0)) & 0xFF;
        int g = buffer.get(getIndex(x, y, 1)) & 0xFF;
        int b = buffer.get(getIndex(x, y, 2)) & 0xFF;
        int a = buffer.get(getIndex(x, y, 3)) & 0xFF;
        return a << 24 | r << 16 | g << 8 | b;
    }
    
    public int getPixelRGBA(int x, int y){
        int r = buffer.get(getIndex(x, y, 0)) & 0xFF;
        int g = buffer.get(getIndex(x, y, 1)) & 0xFF;
        int b = buffer.get(getIndex(x, y, 2)) & 0xFF;
        int a = buffer.get(getIndex(x, y, 3)) & 0xFF;
        return a << 24 | b << 16 | g << 8 | r;
    }

    public Color getPixelColor(int x, int y){
        return new Color(
            (buffer.get(getIndex(x, y, 0)) & 0xFF) / 255F,
            (buffer.get(getIndex(x, y, 1)) & 0xFF) / 255F,
            (buffer.get(getIndex(x, y, 2)) & 0xFF) / 255F,
            (buffer.get(getIndex(x, y, 3)) & 0xFF) / 255F
        );
    }

    public void getPixelColor(int x, int y, Color color){
        color.set(
            (buffer.get(getIndex(x, y, 0)) & 0xFF) / 255F,
            (buffer.get(getIndex(x, y, 1)) & 0xFF) / 255F,
            (buffer.get(getIndex(x, y, 2)) & 0xFF) / 255F,
            (buffer.get(getIndex(x, y, 3)) & 0xFF) / 255F
        );
    }

    // SAMPLE PIXEL
    public int samplePixel(double x, double y){
        return getPixelABGR((int) (x * width), (int) (y * height));
    }

    public IColor samplePixelColor(double x, double y){
        return getPixelColor((int) (x * width), (int) (y * height));
    }

    // FILL
    public void fill(int beginX, int beginY, int endX, int endY, int color){
        float iEnd = Math.min(endX + 1, width);
        float jEnd = Math.min(endY + 1, height);

        for(int i = Math.max(0, beginX); i < iEnd; i++)
            for(int j = Math.max(0, beginY); j < jEnd; j++)
                setPixel(i, j, color);
    }

    public void fill(int beginX, int beginY, int endX, int endY, IColor color){
        float iEnd = Math.min(endX + 1, width);
        float jEnd = Math.min(endY + 1, height);

        for(int i = Math.max(0, beginX); i < iEnd; i++)
            for(int j = Math.max(0, beginY); j < jEnd; j++)
                setPixel(i, j, color);
    }

    public void fill(int beginX, int beginY, int endX, int endY, double r, double g, double b, double a){
        float iEnd = Math.min(endX + 1, width);
        float jEnd = Math.min(endY + 1, height);

        for(int i = Math.max(0, beginX); i < iEnd; i++)
            for(int j = Math.max(0, beginY); j < jEnd; j++)
                setPixel(i, j, r, g, b, a);
    }

    public void fill(int beginX, int beginY, int endX, int endY, int r, int g, int b, int a){
        float iEnd = Math.min(endX + 1, width);
        float jEnd = Math.min(endY + 1, height);

        for(int i = Math.max(0, beginX); i < iEnd; i++)
            for(int j = Math.max(0, beginY); j < jEnd; j++)
                setPixel(i, j, r, g, b, a);
    }

    // DRAW LINE
    public void drawLine(int beginX, int beginY, int endX, int endY, double r, double g, double b, double a){
        Vec2d vec = new Vec2d(endX - beginX, endY - beginY);
        double angle = Math.atan2(vec.y, vec.x);
        double offsetX = Math.cos(angle);
        double offsetY = Math.sin(angle);

        float x = beginX;
        float y = beginY;
        float length = 0;

        while(length < vec.len()){
            setPixel(Maths.round(x), Maths.round(y), r, g, b, a);

            x += offsetX;
            y += offsetY;
            length++;
        }
    }

    // DRAW DOTTED LINE
    public void drawDottedLine(int beginX, int beginY, int endX, int endY, double lineLength, double r, double g, double b, double a){
        Vec2d vec = new Vec2d(endX - beginX, endY - beginY);
        double angle = Math.atan2(vec.y, vec.x);
        double offsetX = Math.cos(angle);
        double offsetY = Math.sin(angle);

        float x = beginX;
        float y = beginY;
        float length = 0;

        while(length < vec.len()){
            if(Math.sin(length / lineLength) >= 0)
                setPixel(Maths.round(x), Maths.round(y), r, g, b, a);

            x += offsetX;
            y += offsetY;
            length++;
        }
    }

    // DRAW PIXMAP
    public void drawPixmap(Pixmap pixmap){
        if(pixmap == null)
            return;
        final float iEnd = Math.min(pixmap.width, width);
        final float jEnd = Math.min(pixmap.height, height);

        for(int i = 0; i < iEnd; i++)
            for(int j = 0; j < jEnd; j++)
                setPixel(i, j, pixmap.getPixelColor(i, j));
    }

    public void drawPixmap(Pixmap pixmap, int x, int y){
        if(pixmap == null)
            return;
        float iEnd = (iEnd = x + pixmap.width) > width ? width : iEnd;
        float jEnd = (jEnd = y + pixmap.height) > height ? height : jEnd;

        for(int i = Math.max(0, x); i < iEnd; i++)
            for(int j = Math.max(0, y); j < jEnd; j++){
                int px = Maths.round(i - x);
                int py = Maths.round(j - y);

                setPixel(i, j, pixmap.getPixelColor(px, py));
            }
    }

    public void drawPixmap(Pixmap pixmap, double scaleX, double scaleY){
        if(pixmap == null || scaleX <= 0 || scaleY <= 0)
            return;
        double iEnd = (iEnd = pixmap.width * scaleX) > width ? width : iEnd;
        double jEnd = (jEnd = pixmap.height * scaleY) > height ? height : jEnd;

        for(int i = 0; i < iEnd; i++)
            for(int j = 0; j < jEnd; j++){
                int px = (int) (i / scaleX);
                int py = (int) (j / scaleY);

                setPixel(i, j, pixmap.getPixelColor(px, py));
            }
    }

    public void drawPixmap(Pixmap pixmap, double scale){
        drawPixmap(pixmap, scale, scale);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, double scaleX, double scaleY){
        if(pixmap == null || scaleX <= 0 || scaleY <= 0)
            return;
        double iEnd = (iEnd = x + pixmap.width * scaleX) > width ? width : iEnd;
        double jEnd = (jEnd = y + pixmap.height * scaleY) > height ? height : jEnd;

        for(int i = Math.max(0, x); i < iEnd; i++)
            for(int j = Math.max(0, y); j < jEnd; j++){
                int px = (int) ((i - x) / scaleX);
                int py = (int) ((j - y) / scaleY);

                setPixel(i, j, pixmap.getPixelColor(px, py));
            }
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, double scale){
        drawPixmap(pixmap, x, y, scale, scale);
    }

    // UTILS
    public void colorize(double r, double g, double b){
        Color color = new Color();
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                getPixelColor(x, y, color);
                color.set(
                    (color.r() * 0.2126 + r) / 2,
                    (color.g() * 0.7152 + g) / 2,
                    (color.b() * 0.0722 + b) / 2
                );

                setPixel(x, y, color);
            }
        }
    }

    public void clearChannel(int channel, double value){
        for(int i = 0; i < buffer.capacity(); i += 4)
            buffer.put(i + channel, (byte) (value * 255));
    }

    // CLEAR
    public void clear(){
        for(int i = 0; i < buffer.capacity(); i++)
            buffer.put(i, (byte) 0);
    }

    public void clear(double r, double g, double b, double a){
        for(int i = 0; i < buffer.capacity(); i += 4){
            buffer.put(i,     (byte) (r * 255));
            buffer.put(i + 1, (byte) (g * 255));
            buffer.put(i + 2, (byte) (b * 255));
            buffer.put(i + 3, (byte) (a * 255));
        }
    }

    public void clear(int r, int g, int b, int a){
        for(int i = 0; i < buffer.capacity(); i += 4){
            buffer.put(i,     (byte) r);
            buffer.put(i + 1, (byte) g);
            buffer.put(i + 2, (byte) b);
            buffer.put(i + 3, (byte) a);
        }
    }

    public void clear(IColor color){
        for(int i = 0; i < buffer.capacity(); i += 4){
            buffer.put(i,     (byte) (color.r() * 255));
            buffer.put(i + 1, (byte) (color.g() * 255));
            buffer.put(i + 2, (byte) (color.b() * 255));
            buffer.put(i + 3, (byte) (color.a() * 255));
        }
    }

    // RESIZE
    @Override
    public void resize(int width, int height){
        if(super.match(width, height))
            return;

        setSize(width, height);
        buffer.clear();
        // Utils.free(buffer);
        buffer = BufferUtils.createByteBuffer(width * height * 4);
    }
    
    // MIPMAPPED
    public Pixmap getMipmapped(){
        Pixmap pixmap = new Pixmap(width / 2, height / 2);
    
        for(int x = 0; x < pixmap.width; x++){
            for(int y = 0; y < pixmap.height; y++){
                pixmap.setPixel(x, y, 1, 0, 0, 1F);
            }
        }
    
        return pixmap;
    }

    // GET BUFFER
    public ByteBuffer getBuffer(){
        return buffer;
    }
    
    public ByteBuffer getAlphaMultipliedBuffer(){
        ByteBuffer buffer = this.buffer.duplicate();
        
        for(int i = 0; i < buffer.capacity(); i += 4){
            float alpha = (buffer.get(i + 3) & 0xFF) / 255F;
            
            buffer.put(i    , (byte) ((int) ((buffer.get(i    ) & 0xFF) * alpha) & 0xFF));
            buffer.put(i + 1, (byte) ((int) ((buffer.get(i + 1) & 0xFF) * alpha) & 0xFF));
            buffer.put(i + 2, (byte) ((int) ((buffer.get(i + 2) & 0xFF) * alpha) & 0xFF));
        }
        
        return buffer;
    }

    // OTHER
    public int getIndex(int x, int y, int channel){
        return (y * width + x) * FORMAT.getChannels() + channel;
    }

    public boolean outOfBounds(int x, int y){
        return x < 0 || y < 0 || x >= width || y >= height;
    }


    public Pixmap copy(){
        return new Pixmap(this);
    }

}
