package jpize.gl.texture;

import static org.lwjgl.opengl.ARBTextureMirrorClampToEdge.GL_MIRROR_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL33.*;

public enum GlWrap{

    REPEAT               (GL_REPEAT),
    MIRRORED_REPEAT      (GL_MIRRORED_REPEAT),
    CLAMP_TO_EDGE        (GL_CLAMP_TO_EDGE),
    CLAMP_TO_BORDER      (GL_CLAMP_TO_BORDER),

    // ARB
    MIRROR_CLAMP_TO_EDGE (GL_MIRROR_CLAMP_TO_EDGE);


    public final int GL;

    GlWrap(int GL){
        this.GL = GL;
    }

}
