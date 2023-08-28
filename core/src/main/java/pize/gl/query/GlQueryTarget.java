package pize.gl.query;

import static org.lwjgl.opengl.ARBOcclusionQuery.*;
import static org.lwjgl.opengl.GL46.*;

public enum GlQueryTarget{
    
    SAMPLES_PASSED                          (GL_SAMPLES_PASSED                       ),
    ANY_SAMPLES_PASSED                      (GL_ANY_SAMPLES_PASSED                   ),
    ANY_SAMPLES_PASSED_CONSERVATIVE         (GL_ANY_SAMPLES_PASSED_CONSERVATIVE      ),
    PRIMITIVES_GENERATED                    (GL_PRIMITIVES_GENERATED                 ),
    TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN   (GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN),
    TIME_ELAPSED                            (GL_TIME_ELAPSED                         ),
    
    SAMPLES_PASSED_ARB                      (GL_SAMPLES_PASSED_ARB                   );
    
    
    public final int GL;
    
    GlQueryTarget(int GL){
        this.GL = GL;
    }
    
}
