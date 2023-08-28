package pize.gl.texture;

import static org.lwjgl.opengl.GL33.*;

public enum GlFormat{
    
    RED             (GL_RED            , 1),
    RED_INTEGER     (GL_RED_INTEGER    , 1),

    RG              (GL_RG             , 2),
    RG_INTEGER      (GL_RG_INTEGER     , 2),

    RGB             (GL_RGB            , 3),
    RGB_INTEGER     (GL_RGB_INTEGER    , 3),
    BGR             (GL_BGR            , 3),
    BGR_INTEGER     (GL_BGR_INTEGER    , 3),

    RGBA            (GL_RGBA           , 4),
    RGBA_INTEGER    (GL_RGBA_INTEGER   , 4),
    BGRA            (GL_BGRA           , 4),
    BGRA_INTEGER    (GL_BGRA_INTEGER   , 4),
    
    STENCIL_INDEX   (GL_STENCIL_INDEX  , 1),
    DEPTH_COMPONENT (GL_DEPTH_COMPONENT, 1),
    DEPTH_STENCIL   (GL_DEPTH_STENCIL  , 1);


    public final int GL;
    private final int channels;

    GlFormat(int GL, int channels){
        this.GL = GL;
        this.channels = channels;
    }

    public int getChannels(){
        return channels;
    }

}
