package jpize.gl.shader;


import static org.lwjgl.opengl.GL32.*;

public enum GlShaderType{

    GEOMETRY(GL_GEOMETRY_SHADER),
    VERTEX(GL_VERTEX_SHADER),
    FRAGMENT(GL_FRAGMENT_SHADER);

    public final int GL;

    GlShaderType(int GL){
        this.GL = GL;
    }

}
