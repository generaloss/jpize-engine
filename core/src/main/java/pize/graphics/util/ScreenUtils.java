package pize.graphics.util;

import org.lwjgl.BufferUtils;
import pize.Pize;
import pize.files.Resource;
import pize.graphics.gl.Format;
import pize.graphics.gl.Type;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.lwjgl.opengl.GL33.glReadPixels;

public class ScreenUtils{

    public static void saveScreenshot(File file, String format){
        final int width = Pize.getWidth();
        final int height = Pize.getHeight();
        
        final ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4).order(ByteOrder.LITTLE_ENDIAN);
        glReadPixels(0, 0, width, height, Format.BGRA.GL, Type.UNSIGNED_BYTE.GL, buffer);
        
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
        final Resource resource = new Resource(filepath);
        saveScreenshot(resource.getFile(), resource.getExtension());
    }

}
