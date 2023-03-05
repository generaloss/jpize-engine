package glit.graphics.util;

import glit.context.Disposable;
import glit.files.FileHandle;
import glit.graphics.texture.CubeMap;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureArray;
import glit.graphics.util.color.IColor;
import glit.math.vecmath.matrix.Matrix4f;
import glit.math.vecmath.tuple.Tuple2f;
import glit.math.vecmath.tuple.Tuple3f;

import java.util.HashMap;

import static org.lwjgl.opengl.GL33.*;

public class Shader implements Disposable{

    private final int id;
    private final HashMap<String, Integer> uniforms;
    private int num_sampler2D, num_samplerCube, num_sampler2DArray;


    public Shader(String vertexCode, String fragmentCode){
        id = glCreateProgram();

        int vertexShaderId = createShader(vertexCode, GL_VERTEX_SHADER);
        int fragmentShaderId = createShader(fragmentCode, GL_FRAGMENT_SHADER);
        glAttachShader(id, vertexShaderId);
        glAttachShader(id, fragmentShaderId);

        glLinkProgram(id);
        if(glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Linking shader error: " + glGetProgramInfoLog(id));
        glValidateProgram(id);
        if(glGetProgrami(id, GL_VALIDATE_STATUS) == GL_FALSE)
            throw new RuntimeException("Validating shader error: " + glGetProgramInfoLog(id));

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        uniforms = new HashMap<>();
        detectUniforms(vertexCode);
        detectUniforms(fragmentCode);
    }

    public Shader(FileHandle vertexFile, FileHandle fragmentFile){
        this(vertexFile.readString(), fragmentFile.readString());
    }

    public Shader(String vertexCode, String fragmentCode, String geometryCode){
        id = glCreateProgram();

        int vertexShaderId = createShader(vertexCode, GL_VERTEX_SHADER);
        int fragmentShaderId = createShader(fragmentCode, GL_FRAGMENT_SHADER);
        int geometryShaderId = createShader(geometryCode, GL_GEOMETRY_SHADER);
        glAttachShader(id, vertexShaderId);
        glAttachShader(id, fragmentShaderId);
        glAttachShader(id, geometryShaderId);

        glLinkProgram(id);
        if(glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Linking shader error: " + glGetProgramInfoLog(id));
        glValidateProgram(id);
        if(glGetProgrami(id, GL_VALIDATE_STATUS) == GL_FALSE)
            throw new RuntimeException("Validating shader error: " + glGetProgramInfoLog(id));

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteShader(geometryShaderId);

        uniforms = new HashMap<>();
        detectUniforms(vertexCode);
        detectUniforms(fragmentCode);
        detectUniforms(geometryCode);
    }

    public Shader(FileHandle vertexFile, FileHandle fragmentFile, FileHandle geometryFile){
        this(vertexFile.readString(), fragmentFile.readString(), geometryFile.readString());
    }


    private int createShader(String code, int shaderType){
        int shaderID = glCreateShader(shaderType);
        glShaderSource(shaderID, code);

        glCompileShader(shaderID);
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException("Compiling shader error: " + glGetShaderInfoLog(shaderID));

        return shaderID;
    }

    private void detectUniforms(String code){                       // '..\nuniform type name [16] ;\n..'
        String[] uniformSplit = code.split("uniform");              // ' type name [16] ;\n..'
        for(int i = 1; i < uniformSplit.length; i++){
            String name = uniformSplit[i].split(";")[0];            // ' type name [16] '
            if(name.contains("["))
                name = name.substring(0, name.lastIndexOf("["));    // ' type name '
            name = name.strip();                                    // 'type name'
            name = name.substring(name.lastIndexOf(" ") + 1);       // 'name'

            uniforms.put(name, glGetUniformLocation(id, name));
        }
    }


    public void setUniform(String uniformName, Matrix4f matrix4f){
        glUniformMatrix4fv(uniforms.get(uniformName), false, matrix4f.val);
    }

    public void setUniform(String uniformName, Tuple2f v){
        glUniform2f(uniforms.get(uniformName), v.x, v.y);
    }

    public void setUniform(String uniformName, Tuple3f v){
        glUniform3f(uniforms.get(uniformName), v.x, v.y, v.z);
    }

    public void setUniform(String uniformName, float x){
        glUniform1f(uniforms.get(uniformName), x);
    }

    public void setUniform(String uniformName, float x, float y){
        glUniform2f(uniforms.get(uniformName), x, y);
    }

    public void setUniform(String uniformName, float x, float y, float z){
        glUniform3f(uniforms.get(uniformName), x, y, z);
    }

    public void setUniform(String uniformName, float x, float y, float z, float w){
        glUniform4f(uniforms.get(uniformName), x, y, z, w);
    }

    public void setUniform(String uniformName, float[] array){
        glUniform1fv(uniforms.get(uniformName), array);
    }

    public void setUniform(String uniformName, int value){
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, int[] array){
        glUniform1iv(uniforms.get(uniformName), array);
    }

    public void setUniform(String uniformName, IColor color){
        glUniform4f(uniforms.get(uniformName), color.r(), color.g(), color.b(), color.a());
    }

    public void setUniform(String uniformName, Texture texture){
        texture.bind(num_sampler2D);
        glUniform1i(uniforms.get(uniformName), num_sampler2D);
        num_sampler2D++;
    }

    public void setUniform(String uniformName, TextureArray textureArray){
        textureArray.bind(num_sampler2DArray);
        glUniform1i(uniforms.get(uniformName), num_sampler2DArray);
        num_sampler2DArray++;
    }

    public void setUniform(String uniformName, CubeMap cubeMap){
        cubeMap.bind(num_samplerCube);
        glUniform1i(uniforms.get(uniformName), num_samplerCube);
        num_samplerCube++;
    }


    public void bindAttribute(int index, String name){
        glBindAttribLocation(id, index, name);
    }

    public int getID(){
        return id;
    }

    public void bind(){
        glUseProgram(id);

        num_sampler2D = 0;
        num_samplerCube = 0;
        num_sampler2DArray = 0;
    }

    public static void unbind(){
        glUseProgram(0);
    }


    @Override
    public void dispose(){
        glDeleteProgram(id);
    }


}
