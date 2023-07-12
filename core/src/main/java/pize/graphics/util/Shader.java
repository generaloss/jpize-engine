package pize.graphics.util;

import pize.files.Resource;
import pize.graphics.gl.GlObject;
import pize.graphics.texture.CubeMap;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureArray;
import pize.graphics.util.color.IColor;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;

import java.util.HashMap;

import static org.lwjgl.opengl.GL33.*;

public class Shader extends GlObject{

    private final HashMap<String, Integer> uniforms;
    private int num_sampler2D, num_samplerCube, num_sampler2DArray;


    public Shader(String vertexCode, String fragmentCode){
        super(glCreateProgram());

        int vertexShaderID = createShader(vertexCode, GL_VERTEX_SHADER);
        int fragmentShaderID = createShader(fragmentCode, GL_FRAGMENT_SHADER);
        glAttachShader(ID, vertexShaderID);
        glAttachShader(ID, fragmentShaderID);

        glLinkProgram(ID);
        if(glGetProgrami(ID, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Linking shader error: " + glGetProgramInfoLog(ID));
        glValidateProgram(ID);
        if(glGetProgrami(ID, GL_VALIDATE_STATUS) == GL_FALSE)
            throw new RuntimeException("Validating shader error: " + glGetProgramInfoLog(ID));

        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);

        uniforms = new HashMap<>();
        detectUniforms(vertexCode);
        detectUniforms(fragmentCode);
    }

    public Shader(Resource resVertex, Resource resFragment){
        this(resVertex.readString(), resFragment.readString());
    }

    public Shader(String vertexCode, String fragmentCode, String geometryCode){
        super(glCreateProgram());

        int vertexShaderID = createShader(vertexCode, GL_VERTEX_SHADER);
        int fragmentShaderID = createShader(fragmentCode, GL_FRAGMENT_SHADER);
        int geometryShaderID = createShader(geometryCode, GL_GEOMETRY_SHADER);
        glAttachShader(ID, vertexShaderID);
        glAttachShader(ID, fragmentShaderID);
        glAttachShader(ID, geometryShaderID);

        glLinkProgram(ID);
        if(glGetProgrami(ID, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Linking shader error: " + glGetProgramInfoLog(ID));
        glValidateProgram(ID);
        if(glGetProgrami(ID, GL_VALIDATE_STATUS) == GL_FALSE)
            throw new RuntimeException("Validating shader error: " + glGetProgramInfoLog(ID));

        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteShader(geometryShaderID);

        uniforms = new HashMap<>();
        detectUniforms(vertexCode);
        detectUniforms(fragmentCode);
        detectUniforms(geometryCode);
    }

    public Shader(Resource resVertex, Resource resFragment, Resource resGeometry){
        this(resVertex.readString(), resFragment.readString(), resGeometry.readString());
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

            uniforms.put(name, glGetUniformLocation(ID, name));
        }
    }


    public void setUniform(String uniformName, Matrix4f matrix4f){
        glUniformMatrix4fv(uniforms.get(uniformName), false, matrix4f.val);
    }

    public void setUniform(String uniformName, Vec2f v){
        glUniform2f(uniforms.get(uniformName), v.x, v.y);
    }

    public void setUniform(String uniformName, Vec3f v){
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
        glBindAttribLocation(ID, index, name);
    }
    
    public void bind(){
        glUseProgram(ID);

        num_sampler2D = 0;
        num_samplerCube = 0;
        num_sampler2DArray = 0;
    }

    public static void unbind(){
        glUseProgram(0);
    }


    @Override
    public void dispose(){
        glDeleteProgram(ID);
    }


}
