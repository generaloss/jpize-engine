package jpize.graphics.texture;

import jpize.files.Resource;
import jpize.graphics.texture.pixmap.PixmapRGBA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

public class PixmapIO{

    public static PixmapRGBA load(BufferedImage bufferedImage, boolean invX, boolean invY){
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        
        final PixmapRGBA pixmap = new PixmapRGBA(width, height);
        final ByteBuffer buffer = pixmap.getBuffer();
        
        final int[] pixels = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                final int pixelIndex = (!invY ? y : height - 1 - y) * width + (!invX ? x : width - 1 - x);
                final int pixel = pixels[pixelIndex];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8 ) & 0xFF));
                buffer.put((byte) ((pixel      ) & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        return pixmap;
    }

    public static PixmapRGBA load(BufferedImage bufferedImage){
        return load(bufferedImage, false, false);
    }


    public static PixmapRGBA load(Resource res, boolean invX, boolean invY){
        try{
            return load(ImageIO.read(res.inStream()), invX, invY);
        }catch(IOException e){
            throw new RuntimeException("Pixmap " + res.inStream().toString() + " does not exists");
        }
    }

    public static PixmapRGBA load(Resource res){
        return load(res, false, false);
    }


    public static PixmapRGBA load(String filepath, boolean invX, boolean invY){
        return load(new Resource(filepath), invX, invY);
    }

    public static PixmapRGBA load(String filepath){
        return load(filepath, false, false);
    }
    
    
    public static void save(PixmapRGBA pixmap, OutputStream output){
        final BufferedImage bufferedImage = new BufferedImage(pixmap.getWidth(), pixmap.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int x = 0; x < pixmap.getWidth(); x++)
            for(int y = 0; y < pixmap.getHeight(); y++)
                bufferedImage.setRGB(x, y, pixmap.getPixelBGRA(x, y));
        
        try{
            ImageIO.write(bufferedImage, "PNG", output);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
    public static void save(PixmapRGBA pixmap, File output){
        try{
            save(pixmap, new FileOutputStream(output));
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    
    public static void save(PixmapRGBA pixmap, String filepath){
        final Resource resource = new Resource(filepath, true);
        resource.mkParentDirs();
        resource.create();
        save(pixmap, resource.getFile());
    }
    
}
