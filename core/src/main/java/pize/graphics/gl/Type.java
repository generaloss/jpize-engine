package pize.graphics.gl;

import static org.lwjgl.opengl.GL44.*;

public enum Type{

    FLOAT                        (GL_FLOAT                       , 4),
    INT                          (GL_INT                         , 4),
    UNSIGNED_INT                 (GL_UNSIGNED_INT                , 4),
    HALF_FLOAT                   (GL_HALF_FLOAT                  , 2),
    SHORT                        (GL_SHORT                       , 2),
    UNSIGNED_SHORT               (GL_UNSIGNED_SHORT              , 2),
    BOOL                         (GL_BOOL                        , 1),
    BYTE                         (GL_BYTE                        , 1),
    UNSIGNED_BYTE                (GL_UNSIGNED_BYTE               , 1),
    DOUBLE                       (GL_DOUBLE                      , 8),
    
    FIXED                        (GL_FIXED                       , 4),
    INT_2_10_10_10_REV           (GL_INT_2_10_10_10_REV          , 4),
    UNSIGNED_INT_2_10_10_10_REV  (GL_UNSIGNED_INT_2_10_10_10_REV , 4),
    UNSIGNED_INT_10F_11F_11F_REV (GL_UNSIGNED_INT_10F_11F_11F_REV, 4)
    ;


    public final int GL;
    private final int size;

    Type(int GL, int size){
        this.GL = GL;
        this.size = size;
    }

    public int getSize(){
        return size;
    }

}
