package glit.graphics.texture;

import glit.graphics.gl.Filter;

import static org.lwjgl.opengl.GL33.*;

public class CubeMap extends GlTexture{
    
    public CubeMap(String px, String nx, String py, String ny, String pz, String nz){
        super(GL_TEXTURE_CUBE_MAP);
        bind();
        
        parameters.setFilter(Filter.LINEAR);
        
        Pixmap pixmapPx = PixmapLoader.loadFrom(px);
        texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X, pixmapPx.getBuffer(), pixmapPx.getWidth(), pixmapPx.getHeight());

        Pixmap pixmapNx = PixmapLoader.loadFrom(nx);
        texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, pixmapNx.getBuffer(), pixmapNx.getWidth(), pixmapNx.getHeight());

        Pixmap pixmapPy = PixmapLoader.loadFrom(py);
        texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, pixmapPy.getBuffer(), pixmapPy.getWidth(), pixmapPy.getHeight());

        Pixmap pixmapNy = PixmapLoader.loadFrom(ny);
        texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, pixmapNy.getBuffer(), pixmapNy.getWidth(), pixmapNy.getHeight());

        Pixmap pixmapPz = PixmapLoader.loadFrom(pz);
        texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, pixmapPz.getBuffer(), pixmapPz.getWidth(), pixmapPz.getHeight());

        Pixmap pixmapNz = PixmapLoader.loadFrom(nz);
        texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, pixmapNz.getBuffer(), pixmapNz.getWidth(), pixmapNz.getHeight());
    
        parameters.use(GL_TEXTURE_CUBE_MAP);
        genMipMap();
    }
    
    public static void unbind(){
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }
    
}
