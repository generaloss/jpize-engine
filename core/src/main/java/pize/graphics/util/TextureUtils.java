package pize.graphics.util;

import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Texture;

public class TextureUtils{

    private static Texture whitePixel;

    public static Texture quadTexture(){
        if(whitePixel == null){
            Pixmap whitePixelPixmap = new Pixmap(1, 1);
            whitePixelPixmap.setPixel(0, 0, 1, 1, 1, 1D);

            whitePixel = new Texture(whitePixelPixmap);
        }

        return whitePixel;
    }

    private static void dispose(){ // Invoked from Context
        if(whitePixel != null)
            whitePixel.dispose();
    }

}
