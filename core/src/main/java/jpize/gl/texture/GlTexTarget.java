package jpize.gl.texture;

import static org.lwjgl.opengl.GL40.*;

public enum GlTexTarget{

    TEXTURE_1D                   (GL_TEXTURE_1D                  ),
    TEXTURE_1D_ARRAY             (GL_TEXTURE_1D_ARRAY            ),

    TEXTURE_2D                   (GL_TEXTURE_2D                  ),
    TEXTURE_2D_ARRAY             (GL_TEXTURE_2D_ARRAY            ),
    TEXTURE_2D_MULTISAMPLE       (GL_TEXTURE_2D_MULTISAMPLE      ),
    TEXTURE_2D_MULTISAMPLE_ARRAY (GL_TEXTURE_2D_MULTISAMPLE_ARRAY),

    TEXTURE_3D                   (GL_TEXTURE_3D                  ),

    RECTANGLE                    (GL_TEXTURE_RECTANGLE           ),
    TEXTURE_BUFFER               (GL_TEXTURE_BUFFER              ),

    CUBE_MAP                     (GL_TEXTURE_CUBE_MAP            ),
    CUBE_MAP_ARRAY               (GL_TEXTURE_CUBE_MAP_ARRAY      );


    public final int GL;

    GlTexTarget(int GL){
        this.GL = GL;
    }

}
