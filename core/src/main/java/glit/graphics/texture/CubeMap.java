package glit.graphics.texture;

import glit.graphics.gl.Filter;

import static org.lwjgl.opengl.GL33.*;

public class CubeMap extends GlTexture{
    
    public CubeMap(String px, String nx, String py, String ny, String pz, String nz){
        super(GL_TEXTURE_CUBE_MAP);
        bind();
        
        parameters.setFilter(Filter.LINEAR);
        
        Pixmap pixmapPx = PixmapLoader.load(px);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X, pixmapPx.getBuffer(), pixmapPx.getWidth(), pixmapPx.getHeight());

        Pixmap pixmapNx = PixmapLoader.load(nx);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, pixmapNx.getBuffer(), pixmapNx.getWidth(), pixmapNx.getHeight());

        Pixmap pixmapPy = PixmapLoader.load(py);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, pixmapPy.getBuffer(), pixmapPy.getWidth(), pixmapPy.getHeight());

        Pixmap pixmapNy = PixmapLoader.load(ny);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, pixmapNy.getBuffer(), pixmapNy.getWidth(), pixmapNy.getHeight());

        Pixmap pixmapPz = PixmapLoader.load(pz);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, pixmapPz.getBuffer(), pixmapPz.getWidth(), pixmapPz.getHeight());

        Pixmap pixmapNz = PixmapLoader.load(nz);
        parameters.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, pixmapNz.getBuffer(), pixmapNz.getWidth(), pixmapNz.getHeight());
    
        parameters.use(GL_TEXTURE_CUBE_MAP);
        genMipMap();
    }
    
    public static void unbind(){
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }
    
}
