package jpize.gl.glenum;

import static org.lwjgl.opengl.ARBImaging.*;
import static org.lwjgl.opengl.GL33.*;

public enum GlTarget{

    BLEND                         (GL_BLEND                        ),
    CULL_FACE                     (GL_CULL_FACE                    ),
    DEPTH_TEST                    (GL_DEPTH_TEST                   ),
    TEXTURE_2D                    (GL_TEXTURE_2D                   ),

    ALPHA_TEST                    (GL_ALPHA_TEST                   ),
    AUTO_NORMAL                   (GL_AUTO_NORMAL                  ),
    COLOR_LOGIC_OP                (GL_COLOR_LOGIC_OP               ),
    COLOR_MATERIAL                (GL_COLOR_MATERIAL               ),
    COLOR_SUM                     (GL_COLOR_SUM                    ),
    FOG                           (GL_FOG                          ),
    DITHER                        (GL_DITHER                       ),
    INDEX_LOGIC_OP                (GL_INDEX_LOGIC_OP               ),
    LIGHTING                      (GL_LIGHTING                     ),
    LINE_SMOOTH                   (GL_LINE_SMOOTH                  ),
    LINE_STIPPLE                  (GL_LINE_STIPPLE                 ),
    MULTISAMPLE                   (GL_MULTISAMPLE                  ),
    NORMALIZE                     (GL_NORMALIZE                    ),
    POINT_SMOOTH                  (GL_POINT_SMOOTH                 ),
    POINT_SPRITE                  (GL_POINT_SPRITE                 ),
    POLYGON_OFFSET_FILL           (GL_POLYGON_OFFSET_FILL          ),
    POLYGON_OFFSET_LINE           (GL_POLYGON_OFFSET_LINE          ),
    POLYGON_OFFSET_POINT          (GL_POLYGON_OFFSET_POINT         ),
    POLYGON_SMOOTH                (GL_POLYGON_SMOOTH               ),
    POLYGON_STIPPLE               (GL_POLYGON_STIPPLE              ),
    RESCALE_NORMAL                (GL_RESCALE_NORMAL               ),
    SAMPLE_ALPHA_TO_COVERAGE      (GL_SAMPLE_ALPHA_TO_COVERAGE     ),
    SAMPLE_ALPHA_TO_ONE           (GL_SAMPLE_ALPHA_TO_ONE          ),
    SAMPLE_COVERAGE               (GL_SAMPLE_COVERAGE              ),
    SCISSOR_TEST                  (GL_SCISSOR_TEST                 ),
    STENCIL_TEST                  (GL_STENCIL_TEST                 ),
    TEXTURE_1D                    (GL_TEXTURE_1D                   ),
    TEXTURE_3D                    (GL_TEXTURE_3D                   ),
    TEXTURE_CUBE_MAP              (GL_TEXTURE_CUBE_MAP             ),
    TEXTURE_GEN_Q                 (GL_TEXTURE_GEN_Q                ),
    TEXTURE_GEN_R                 (GL_TEXTURE_GEN_R                ),
    TEXTURE_GEN_S                 (GL_TEXTURE_GEN_S                ),
    TEXTURE_GEN_T                 (GL_TEXTURE_GEN_T                ),
    VERTEX_PROGRAM_POINT_SIZE     (GL_VERTEX_PROGRAM_POINT_SIZE    ),
    VERTEX_PROGRAM_TWO_SIDE       (GL_VERTEX_PROGRAM_TWO_SIDE      ),

    MAP1_COLOR_4                  (GL_MAP1_COLOR_4                 ),
    MAP1_INDEX                    (GL_MAP1_INDEX                   ),
    MAP1_NORMAL                   (GL_MAP1_NORMAL                  ),
    MAP1_TEXTURE_COORD_1          (GL_MAP1_TEXTURE_COORD_1         ),
    MAP1_TEXTURE_COORD_2          (GL_MAP1_TEXTURE_COORD_2         ),
    MAP1_TEXTURE_COORD_3          (GL_MAP1_TEXTURE_COORD_3         ),
    MAP1_TEXTURE_COORD_4          (GL_MAP1_TEXTURE_COORD_4         ),
    MAP1_VERTEX_3                 (GL_MAP1_VERTEX_3                ),
    MAP1_VERTEX_4                 (GL_MAP1_VERTEX_4                ),

    MAP2_COLOR_4                  (GL_MAP2_COLOR_4                 ),
    MAP2_INDEX                    (GL_MAP2_INDEX                   ),
    MAP2_NORMAL                   (GL_MAP2_NORMAL                  ),
    MAP2_TEXTURE_COORD_1          (GL_MAP2_TEXTURE_COORD_1         ),
    MAP2_TEXTURE_COORD_2          (GL_MAP2_TEXTURE_COORD_2         ),
    MAP2_TEXTURE_COORD_3          (GL_MAP2_TEXTURE_COORD_3         ),
    MAP2_TEXTURE_COORD_4          (GL_MAP2_TEXTURE_COORD_4         ),
    MAP2_VERTEX_3                 (GL_MAP2_VERTEX_3                ),
    MAP2_VERTEX_4                 (GL_MAP2_VERTEX_4                ),

    CLIP_PLANE0                   (GL_CLIP_PLANE0                  ),
    CLIP_PLANE1                   (GL_CLIP_PLANE1                  ),
    CLIP_PLANE2                   (GL_CLIP_PLANE2                  ),
    CLIP_PLANE3                   (GL_CLIP_PLANE3                  ),
    CLIP_PLANE4                   (GL_CLIP_PLANE4                  ),
    CLIP_PLANE5                   (GL_CLIP_PLANE5                  ),

    LIGHT0                        (GL_LIGHT0                       ),
    LIGHT1                        (GL_LIGHT1                       ),
    LIGHT2                        (GL_LIGHT2                       ),
    LIGHT3                        (GL_LIGHT3                       ),
    LIGHT4                        (GL_LIGHT4                       ),
    LIGHT5                        (GL_LIGHT5                       ),
    LIGHT6                        (GL_LIGHT6                       ),
    LIGHT7                        (GL_LIGHT7                       ),

    // ARB
    COLOR_TABLE                   (GL_COLOR_TABLE                  ),
    CONVOLUTION_1D                (GL_CONVOLUTION_1D               ),
    CONVOLUTION_2D                (GL_CONVOLUTION_2D               ),
    HISTOGRAM                     (GL_HISTOGRAM                    ),
    MINMAX                        (GL_MINMAX                       ),
    POST_COLOR_MATRIX_COLOR_TABLE (GL_POST_COLOR_MATRIX_COLOR_TABLE),
    POST_CONVOLUTION_COLOR_TABLE  (GL_POST_CONVOLUTION_COLOR_TABLE ),
    SEPARABLE_2D                  (GL_SEPARABLE_2D                 );


    public final int GL;

    GlTarget(int GL){
        this.GL = GL;
    }

}
