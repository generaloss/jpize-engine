package glit.graphics.texture;

import glit.context.Disposable;

import static org.lwjgl.opengl.GL33.*;

public class CubeMap implements Disposable{

    private final int id;
    private final TextureParameters parameters;

    public CubeMap(String px, String nx, String py, String ny, String pz, String nz){
        parameters = new TextureParameters();

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, id);

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        Pixmap pixmapPx = PixmapLoader.loadFrom(px);
        glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0,
            parameters.internalFormat.gl, pixmapPx.getWidth(), pixmapPx.getHeight(), 0,
            parameters.internalFormat.getBaseFormat().gl, parameters.type.gl, pixmapPx.getBuffer()
        );

        Pixmap pixmapNx = PixmapLoader.loadFrom(nx);
        glTexImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0,
            parameters.internalFormat.gl, pixmapNx.getWidth(), pixmapNx.getHeight(), 0,
            parameters.internalFormat.getBaseFormat().gl, parameters.type.gl, pixmapNx.getBuffer()
        );

        Pixmap pixmapPy = PixmapLoader.loadFrom(py);
        glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0,
            parameters.internalFormat.gl, pixmapPy.getWidth(), pixmapPy.getHeight(), 0,
            parameters.internalFormat.getBaseFormat().gl, parameters.type.gl, pixmapPy.getBuffer()
        );

        Pixmap pixmapNy = PixmapLoader.loadFrom(ny);
        glTexImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0,
            parameters.internalFormat.gl, pixmapNy.getWidth(), pixmapNy.getHeight(), 0,
            parameters.internalFormat.getBaseFormat().gl, parameters.type.gl, pixmapNy.getBuffer()
        );

        Pixmap pixmapPz = PixmapLoader.loadFrom(pz);
        glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0,
            parameters.internalFormat.gl, pixmapPz.getWidth(), pixmapPz.getHeight(), 0,
            parameters.internalFormat.getBaseFormat().gl, parameters.type.gl, pixmapPz.getBuffer()
        );

        Pixmap pixmapNz = PixmapLoader.loadFrom(nz);
        glTexImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0,
            parameters.internalFormat.gl, pixmapNz.getWidth(), pixmapNz.getHeight(), 0,
            parameters.internalFormat.getBaseFormat().gl, parameters.type.gl, pixmapNz.getBuffer()
        );
    }


    public TextureParameters getParameters(){
        return parameters;
    }


    public void bind(int num){
        glActiveTexture(GL_TEXTURE0 + num);
        glBindTexture(GL_TEXTURE_CUBE_MAP, id);
    }

    @Override
    public void dispose(){
        glDeleteTextures(id);
    }

}
