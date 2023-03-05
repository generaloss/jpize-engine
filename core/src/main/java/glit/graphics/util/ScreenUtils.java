package glit.graphics.util;

import glit.Glit;
import glit.files.FileHandle;
import glit.graphics.gl.Format;
import glit.graphics.gl.Type;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.lwjgl.opengl.GL33.glReadPixels;

public class ScreenUtils{

    public static void saveScreenshot(File file, String format){
        int width = Glit.getWidth();
        int height = Glit.getHeight();

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.LITTLE_ENDIAN);
        glReadPixels(0, 0, width, height, Format.BGRA.gl, Type.UNSIGNED_BYTE.gl, buffer);

        int[] pixels = new int[width * height];
        buffer.asIntBuffer().get(pixels);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
        FileHandle file = new FileHandle(filepath);
        saveScreenshot(file.getFile(), file.extension());
    }

}
