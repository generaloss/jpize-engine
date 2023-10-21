package jpize.graphics.texture;

import jpize.gl.texture.GlFilter;
import jpize.gl.texture.GlTexParam;
import jpize.gl.texture.GlTexTarget;
import jpize.graphics.texture.pixmap.PixmapRGBA;

import static org.lwjgl.opengl.GL33.*;

public class CubeMap extends GlTexture{
    
    public CubeMap(String positive_x, String negative_x,
                   String positive_y, String negative_y,
                   String positive_z, String negative_z){

        super(GlTexTarget.CUBE_MAP);
        bind();
        
        parameters.setFilter(GlFilter.LINEAR);
        
        final PixmapRGBA pixmapPX = PixmapIO.load(positive_x);
        parameters.texImage2D(GlTexParam.CUBE_MAP_POSITIVE_X, pixmapPX.getBuffer(), pixmapPX.getWidth(), pixmapPX.getHeight());

        final PixmapRGBA pixmapNX = PixmapIO.load(negative_x);
        parameters.texImage2D(GlTexParam.CUBE_MAP_NEGATIVE_X, pixmapNX.getBuffer(), pixmapNX.getWidth(), pixmapNX.getHeight());

        final PixmapRGBA pixmapPY = PixmapIO.load(positive_y);
        parameters.texImage2D(GlTexParam.CUBE_MAP_POSITIVE_Y, pixmapPY.getBuffer(), pixmapPY.getWidth(), pixmapPY.getHeight());

        final PixmapRGBA pixmapNY = PixmapIO.load(negative_y);
        parameters.texImage2D(GlTexParam.CUBE_MAP_NEGATIVE_Y, pixmapNY.getBuffer(), pixmapNY.getWidth(), pixmapNY.getHeight());

        final PixmapRGBA pixmapPZ = PixmapIO.load(positive_z);
        parameters.texImage2D(GlTexParam.CUBE_MAP_POSITIVE_Z, pixmapPZ.getBuffer(), pixmapPZ.getWidth(), pixmapPZ.getHeight());

        final PixmapRGBA pixmapNZ = PixmapIO.load(negative_z);
        parameters.texImage2D(GlTexParam.CUBE_MAP_NEGATIVE_Z, pixmapNZ.getBuffer(), pixmapNZ.getWidth(), pixmapNZ.getHeight());
    
        parameters.use(GlTexTarget.CUBE_MAP);
        genMipMap();
    }
    
    public static void unbind(){
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }
    
}
