package pize.graphics.texture;

import pize.gl.texture.GlFilter;

import static org.lwjgl.opengl.GL33.*;

public class CubeMap extends GlTexture{
    
    public CubeMap(String px, String nx, String py, String ny, String pz, String nz){
        super(GL_TEXTURE_CUBE_MAP);
        bind();
        
        parameters.setFilter(GlFilter.LINEAR);
        
        Pixmap pixmapPx = PixmapIO.load(px);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X, pixmapPx.getBuffer(), pixmapPx.getWidth(), pixmapPx.getHeight());

        Pixmap pixmapNx = PixmapIO.load(nx);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, pixmapNx.getBuffer(), pixmapNx.getWidth(), pixmapNx.getHeight());

        Pixmap pixmapPy = PixmapIO.load(py);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, pixmapPy.getBuffer(), pixmapPy.getWidth(), pixmapPy.getHeight());

        Pixmap pixmapNy = PixmapIO.load(ny);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, pixmapNy.getBuffer(), pixmapNy.getWidth(), pixmapNy.getHeight());

        Pixmap pixmapPz = PixmapIO.load(pz);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, pixmapPz.getBuffer(), pixmapPz.getWidth(), pixmapPz.getHeight());

        Pixmap pixmapNz = PixmapIO.load(nz);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, pixmapNz.getBuffer(), pixmapNz.getWidth(), pixmapNz.getHeight());
    
        parameters.use(GL_TEXTURE_CUBE_MAP);
        genMipMap();
    }
    
    public static void unbind(){
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }
    
}
