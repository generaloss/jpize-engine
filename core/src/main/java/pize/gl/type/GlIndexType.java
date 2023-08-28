package pize.gl.type;

import static org.lwjgl.opengl.GL11.*;

public enum GlIndexType{

    UNSIGNED_INT                 (GL_UNSIGNED_INT                , 4),
    UNSIGNED_SHORT               (GL_UNSIGNED_SHORT              , 2),
    UNSIGNED_BYTE                (GL_UNSIGNED_BYTE               , 1);

    public final int GL;
    private final int size;

    GlIndexType(int GL, int size){
        this.GL = GL;
        this.size = size;
    }

    public int getSize(){
        return size;
    }

}