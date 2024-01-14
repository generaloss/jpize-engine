package jpize.sdl.image;

import io.github.libsdl4j.api.surface.SDL_Surface;
import io.github.libsdl4j.api.surface.SdlSurface;
import jpize.util.file.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

public class SdlImage{

    public static SDL_Surface load(BufferedImage bufferedImage, boolean invX, boolean invY){
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();

        final SDL_Surface surface = SdlSurface.SDL_CreateRGBSurface(
            0, width, height, 32,
            0x00ff0000, 0x0000ff00, 0x000000ff, 0xff000000
        );

        final IntBuffer buffer = surface.getPixels().getByteBuffer(0, width * height * 4L).asIntBuffer();

        final int[] pixels = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                final int pixelIndex = (!invY ? y : height - 1 - y) * width + (!invX ? x : width - 1 - x);
                final int pixel = pixels[pixelIndex];
                buffer.put(pixel);
            }
        }
        buffer.flip();

        return surface;
    }

    public static SDL_Surface load(BufferedImage bufferedImage){
        return load(bufferedImage, false, false);
    }


    public static SDL_Surface load(Resource res, boolean invX, boolean invY){
        try{
            return load(ImageIO.read(res.inStream()), invX, invY);
        }catch(IOException e){
            throw new RuntimeException("Pixmap " + res.asInternal().resName() + " does not exists");
        }
    }

    public static SDL_Surface load(Resource res){
        return load(res, false, false);
    }


    public static SDL_Surface load(String filepath, boolean invX, boolean invY){
        return load(Resource.internal(filepath), invX, invY);
    }

    public static SDL_Surface load(String filepath){
        return load(filepath, false, false);
    }

}
