package jpize.gl.shader;

import jpize.gl.GlObject;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL32.*;

public class GlProgram extends GlObject{

    public GlProgram(){
        super(glCreateProgram());
    }


    public void attach(GlShader shader){
        glAttachShader(ID, shader.getID());
    }

    public void link(){
        glLinkProgram(ID);
        if(glGetProgrami(ID, GL_LINK_STATUS) == GL_FALSE)
            logError("Linking shader error");

        glValidateProgram(ID);
        if(glGetProgrami(ID, GL_VALIDATE_STATUS) == GL_FALSE)
            logError("Validating shader error");
    }

    private void logError(String error){
        final String log = glGetProgramInfoLog(ID);
        if(!log.isEmpty())
            throw new RuntimeException(error + ":\n" + log);
        else
            throw new RuntimeException(error);
    }


    public int getUniformLocation(CharSequence uniform){
        return glGetUniformLocation(ID, uniform);
    }

    public void bindAttribute(int index, String name){
        glBindAttribLocation(ID, index, name);
    }

    public void bindFragData(int index, String name){
        glBindFragDataLocation(ID, index, name);
    }


    public void setUniform(int location, float x){
        glUniform1f(location, x);
    }

    public void setUniform(int location, int x){
        glUniform1i(location, x);
    }

    public void setUniform(int location, float x, float y){
        glUniform2f(location, x, y);
    }

    public void setUniform(int location, int x, int y){
        glUniform2i(location, x, y);
    }

    public void setUniform(int location, float x, float y, float z){
        glUniform3f(location, x, y, z);
    }

    public void setUniform(int location, int x, int y, int z){
        glUniform3i(location, x, y, z);
    }

    public void setUniform(int location, float x, float y, float z, float w){
        glUniform4f(location, x, y, z, w);
    }

    public void setUniform(int location, int x, int y, int z, int w){
        glUniform4i(location, x, y, z, w);
    }


    public void setUniformMat4(int location, boolean transpose, float[] values){
        glUniformMatrix4fv(location, transpose, values);
    }

    public void setUniformMat4x3(int location, boolean transpose, float[] values){
        glUniformMatrix4x3fv(location, transpose, values);
    }

    public void setUniformMat4x2(int location, boolean transpose, float[] values){
        glUniformMatrix4x2fv(location, transpose, values);
    }


    public void setUniformMat3x4(int location, boolean transpose, float[] values){
        glUniformMatrix3x4fv(location, transpose, values);
    }

    public void setUniformMat3(int location, boolean transpose, float[] values){
        glUniformMatrix3fv(location, transpose, values);
    }

    public void setUniformMat3x2(int location, boolean transpose, float[] values){
        glUniformMatrix3x2fv(location, transpose, values);
    }


    public void setUniformMat2x4(int location, boolean transpose, float[] values){
        glUniformMatrix2x4fv(location, transpose, values);
    }

    public void setUniformMat2x3(int location, boolean transpose, float[] values){
        glUniformMatrix2x3fv(location, transpose, values);
    }

    public void setUniformMat2(int location, boolean transpose, float[] values){
        glUniformMatrix2fv(location, transpose, values);
    }


    public void setUniform(int location, float[] array){
        glUniform1fv(location, array);
    }

    public void setUniform(int location, int[] array){
        glUniform1iv(location, array);
    }

    public void setUniform(int location, FloatBuffer buffer){
        glUniform1fv(location, buffer);
    }

    public void setUniform(int location, IntBuffer buffer){
        glUniform1iv(location, buffer);
    }


    public float setUniformFloat(int location){
        return glGetUniformf(ID, location);
    }

    public int setUniformInt(int location){
        return glGetUniformi(ID, location);
    }


    public void bindUniformBlock(int location, int bindingPoint){
        glUniformBlockBinding(ID, location, bindingPoint);
    }


    public void bind(){
        glUseProgram(ID);
    }

    public static void unbind(){
        glUseProgram(0);
    }


    @Override
    public void dispose(){
        glDeleteProgram(ID);
    }

}
