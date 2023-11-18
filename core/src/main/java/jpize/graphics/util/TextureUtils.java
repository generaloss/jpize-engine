package jpize.graphics.util;

import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.texture.Texture;

public class TextureUtils{

    private static Texture whitePixel;

    public static Texture quadTexture(){
        if(whitePixel == null){
            final PixmapRGBA whitePixelPixmap = new PixmapRGBA(1, 1);
            whitePixelPixmap.setPixel(0, 0, 1D, 1D, 1D, 1D);

            whitePixel = new Texture(whitePixelPixmap);
        }

        return whitePixel;
    }

    private static void dispose(){ // Calls from ContextManager
        if(whitePixel != null)
            whitePixel.dispose();
    }

}
