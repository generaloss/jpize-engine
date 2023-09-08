package jpize.gl.texture;

import static org.lwjgl.opengl.GL40.*;

public enum GlTexParam{

    TEXTURE_1D_ARRAY       (GL_TEXTURE_1D_ARRAY           ),
    PROXY_TEXTURE_1D_ARRAY (GL_PROXY_TEXTURE_1D_ARRAY     ),

    TEXTURE_2D_ARRAY       (GL_TEXTURE_2D_ARRAY           ),
    PROXY_TEXTURE_2D_ARRAY (GL_PROXY_TEXTURE_2D_ARRAY     ),
    TEXTURE_2D             (GL_TEXTURE_2D                 ),
    PROXY_TEXTURE_2D       (GL_PROXY_TEXTURE_2D           ),

    TEXTURE_3D             (GL_TEXTURE_3D                 ),
    PROXY_TEXTURE_3D       (GL_PROXY_TEXTURE_3D           ),

    RECTANGLE              (GL_TEXTURE_RECTANGLE          ),
    PROXY_RECTANGLE        (GL_PROXY_TEXTURE_RECTANGLE    ),

    CUBE_MAP_POSITIVE_X    (GL_TEXTURE_CUBE_MAP_POSITIVE_X),
    CUBE_MAP_NEGATIVE_X    (GL_TEXTURE_CUBE_MAP_NEGATIVE_X),
    CUBE_MAP_POSITIVE_Y    (GL_TEXTURE_CUBE_MAP_POSITIVE_Y),
    CUBE_MAP_NEGATIVE_Y    (GL_TEXTURE_CUBE_MAP_NEGATIVE_Y),
    CUBE_MAP_POSITIVE_Z    (GL_TEXTURE_CUBE_MAP_POSITIVE_Z),
    CUBE_MAP_NEGATIVE_Z    (GL_TEXTURE_CUBE_MAP_NEGATIVE_Z),
    PROXY_CUBE_MAP         (GL_PROXY_TEXTURE_CUBE_MAP     );


    public final int GL;

    GlTexParam(int GL){
        this.GL = GL;
    }

}
