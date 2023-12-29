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


    public void uniform(int location, float x){
        glUniform1f(location, x);
    }

    public void uniform(int location, int x){
        glUniform1i(location, x);
    }

    public void uniform(int location, float x, float y){
        glUniform2f(location, x, y);
    }

    public void uniform(int location, int x, int y){
        glUniform2i(location, x, y);
    }

    public void uniform(int location, float x, float y, float z){
        glUniform3f(location, x, y, z);
    }

    public void uniform(int location, int x, int y, int z){
        glUniform3i(location, x, y, z);
    }

    public void uniform(int location, float x, float y, float z, float w){
        glUniform4f(location, x, y, z, w);
    }

    public void uniform(int location, int x, int y, int z, int w){
        glUniform4i(location, x, y, z, w);
    }


    public void uniformMat4(int location, boolean transpose, float[] values){
        glUniformMatrix4fv(location, transpose, values);
    }

    public void uniformMat4x3(int location, boolean transpose, float[] values){
        glUniformMatrix4x3fv(location, transpose, values);
    }

    public void uniformMat4x2(int location, boolean transpose, float[] values){
        glUniformMatrix4x2fv(location, transpose, values);
    }


    public void uniformMat3x4(int location, boolean transpose, float[] values){
        glUniformMatrix3x4fv(location, transpose, values);
    }

    public void uniformMat3(int location, boolean transpose, float[] values){
        glUniformMatrix3fv(location, transpose, values);
    }

    public void uniformMat3x2(int location, boolean transpose, float[] values){
        glUniformMatrix3x2fv(location, transpose, values);
    }


    public void uniformMat2x4(int location, boolean transpose, float[] values){
        glUniformMatrix2x4fv(location, transpose, values);
    }

    public void uniformMat2x3(int location, boolean transpose, float[] values){
        glUniformMatrix2x3fv(location, transpose, values);
    }

    public void uniformMat2(int location, boolean transpose, float[] values){
        glUniformMatrix2fv(location, transpose, values);
    }


    public void uniform(int location, float[] array){
        glUniform1fv(location, array);
    }

    public void uniform(int location, int[] array){
        glUniform1iv(location, array);
    }

    public void uniform(int location, FloatBuffer buffer){
        glUniform1fv(location, buffer);
    }

    public void uniform(int location, IntBuffer buffer){
        glUniform1iv(location, buffer);
    }


    public int getUniformLocation(CharSequence uniform){
        return glGetUniformLocation(ID, uniform);
    }

    public float getUniformFloat(int location){
        return glGetUniformf(ID, location);
    }

    public int getUniformInt(int location){
        return glGetUniformi(ID, location);
    }

    public void bindAttributeLoc(int index, String name){
        glBindAttribLocation(ID, index, name);
    }

    public void bindFragDataLoc(int index, String name){
        glBindFragDataLocation(ID, index, name);
    }


    public int getUniformBlockIndex(String uniformBlockName){
        return glGetUniformBlockIndex(ID, uniformBlockName);
    }

    public void uniformBlockBinding(int location, int bindingPoint){
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
