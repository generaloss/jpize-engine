package glit.graphics.texture;

import glit.files.FileHandle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

public class PixmapLoader{

    public static Pixmap load(BufferedImage bufferedImage, boolean invX, boolean invY){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        Pixmap pixmap = new Pixmap(width, height);
        ByteBuffer buffer = pixmap.getBuffer();

        int[] pixels = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int pixelIndex = (!invY ? y : height - 1 - y) * width + (!invX ? x : width - 1 - x);
                int pixel = pixels[pixelIndex];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) ((pixel) & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        return pixmap;
    }

    public static Pixmap load(BufferedImage bufferedImage){
        return load(bufferedImage, false, false);
    }


    public static Pixmap load(FileHandle file, boolean invX, boolean invY){
        try{
            return load(ImageIO.read(file.input()), invX, invY);
        }catch(IOException e){
            throw new RuntimeException("Pixmap " + file.input().toString() + " does not exists");
        }
    }

    public static Pixmap load(FileHandle file){
        return load(file, false, false);
    }


    public static Pixmap load(String filepath, boolean invX, boolean invY){
        return load(new FileHandle(filepath), invX, invY);
    }

    public static Pixmap load(String filepath){
        return load(filepath, false, false);
    }

}
