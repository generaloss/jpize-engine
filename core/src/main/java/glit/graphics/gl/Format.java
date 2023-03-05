package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum Format{

    DEPTH_COMPONENT (GL_DEPTH_COMPONENT, 1),
    DEPTH_STENCIL   (GL_DEPTH_STENCIL  , 2),

    RED             (GL_RED            , 1),
    GREEN           (GL_GREEN          , 1),
    BLUE            (GL_BLUE           , 1),
    ALPHA           (GL_ALPHA          , 1),

    RG              (GL_RG             , 2),

    RGB             (GL_RGB            , 3),
    BGR             (GL_BGR            , 3),

    RGBA            (GL_RGBA           , 4),
    BGRA            (GL_BGRA           , 4);


    public final int gl;
    private final int channels;

    Format(int gl, int channels){
        this.gl = gl;
        this.channels = channels;
    }

    public int getChannels(){
        return channels;
    }

}
