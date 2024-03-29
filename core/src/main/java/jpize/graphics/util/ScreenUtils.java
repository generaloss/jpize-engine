package jpize.graphics.util;

import org.lwjgl.BufferUtils;
import jpize.Jpize;
import jpize.util.res.Resource;
import jpize.gl.texture.GlFormat;
import jpize.gl.type.GlType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.lwjgl.opengl.GL33.glReadPixels;

public class ScreenUtils{

    public static void saveScreenshot(File file, String format){
        final int width = Jpize.getWidth();
        final int height = Jpize.getHeight();
        
        final ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4).order(ByteOrder.LITTLE_ENDIAN);
        glReadPixels(0, 0, width, height, GlFormat.BGRA.GL, GlType.UNSIGNED_BYTE.GL, buffer);
        
        final int[] pixels = new int[width * height];
        buffer.asIntBuffer().get(pixels);
        
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                bufferedImage.setRGB(x, height - y - 1, pixels[y * width + x]);

        try{
            ImageIO.write(bufferedImage, format, file);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void saveScreenshot(String filepath){
        final Resource resource = Resource.external(filepath);
        saveScreenshot(resource.file(), resource.extension());
    }

}
