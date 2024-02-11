package jpize.graphics.util;

import jpize.gl.shader.GlProgram;
import jpize.gl.shader.GlShader;
import jpize.gl.shader.GlShaderType;
import jpize.graphics.texture.CubeMap;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureArray;
import jpize.util.color.IColor;
import jpize.util.math.matrix.Matrix3f;
import jpize.util.math.matrix.Matrix4f;
import jpize.util.math.vector.Vec2f;
import jpize.util.math.vector.Vec3f;
import jpize.app.Disposable;
import jpize.util.res.Resource;

import java.util.HashMap;

public class Shader implements Disposable{

    private final GlProgram program;
    private final HashMap<String, Integer> uniforms;
    private int num_sampler2D, num_samplerCube, num_sampler2DArray;


    public Shader(String vertexCode, String fragmentCode){
        this.program = new GlProgram();

        final GlShader vertexShader = new GlShader(GlShaderType.VERTEX, vertexCode);
        final GlShader fragmentShader = new GlShader(GlShaderType.FRAGMENT, fragmentCode);

        this.program.attach(vertexShader);
        this.program.attach(fragmentShader);
        this.program.link();

        vertexShader.dispose();
        fragmentShader.dispose();

        this.uniforms = new HashMap<>();
        detectUniforms(vertexCode);
        detectUniforms(fragmentCode);
    }

    public Shader(Resource resVertex, Resource resFragment){
        this(resVertex.readString(), resFragment.readString());
    }

    public Shader(String vertexCode, String fragmentCode, String geometryCode){
        this.program = new GlProgram();

        final GlShader geometryShader = new GlShader(GlShaderType.GEOMETRY, geometryCode);
        final GlShader vertexShader = new GlShader(GlShaderType.VERTEX, vertexCode);
        final GlShader fragmentShader = new GlShader(GlShaderType.FRAGMENT, fragmentCode);

        this.program.attach(geometryShader);
        this.program.attach(vertexShader);
        this.program.attach(fragmentShader);
        this.program.link();

        geometryShader.dispose();
        vertexShader.dispose();
        fragmentShader.dispose();

        this.uniforms = new HashMap<>();
        detectUniforms(vertexCode);
        detectUniforms(fragmentCode);
        detectUniforms(geometryCode);
    }

    public Shader(Resource resVertex, Resource resFragment, Resource resGeometry){
        this(resVertex.readString(), resFragment.readString(), resGeometry.readString());
    }

    private void detectUniforms(String code){                       // '..\nuniform type name [16] ;\n..'
        final String[] uniformSplit = code.split("uniform");        // ' type name [16] ;\n..'
        for(int i = 1; i < uniformSplit.length; i++){
            String name = uniformSplit[i].split(";")[0];            // ' type name [16] '
            if(name.contains("["))
                name = name.substring(0, name.lastIndexOf("["));    // ' type name '
            name = name.strip();                                    // 'type name'
            name = name.substring(name.lastIndexOf(" ") + 1);       // 'name'

            uniforms.put(name, program.getUniformLocation(name));
        }
    }


    public void uniformMat4(String uniformName, float[] values){
        program.uniformMat4(uniforms.get(uniformName), false, values);
    }

    public void uniform(String uniformName, Matrix4f matrix4f){
        uniformMat4(uniformName, matrix4f.val);
    }

    public void uniformMat3(String uniformName, float[] values){
        program.uniformMat3(uniforms.get(uniformName), false, values);
    }

    public void uniform(String uniformName, Matrix3f matrix3f){
        uniformMat3(uniformName, matrix3f.val);
    }

    public void uniform(String uniformName, Vec2f v){
        program.uniform(uniforms.get(uniformName), v.x, v.y);
    }

    public void uniform(String uniformName, Vec3f v){
        program.uniform(uniforms.get(uniformName), v.x, v.y, v.z);
    }

    public void uniform(String uniformName, float x){
        program.uniform(uniforms.get(uniformName), x);
    }

    public void uniform(String uniformName, float x, float y){
        program.uniform(uniforms.get(uniformName), x, y);
    }

    public void uniform(String uniformName, float x, float y, float z){
        program.uniform(uniforms.get(uniformName), x, y, z);
    }

    public void uniform(String uniformName, float x, float y, float z, float w){
        program.uniform(uniforms.get(uniformName), x, y, z, w);
    }

    public void uniform(String uniformName, float[] array){
        program.uniform(uniforms.get(uniformName), array);
    }

    public void uniform(String uniformName, int value){
        program.uniform(uniforms.get(uniformName), value);
    }

    public void uniform(String uniformName, boolean value){
        uniform(uniformName, value ? 1 : 0);
    }

    public void uniform(String uniformName, int[] array){
        program.uniform(uniforms.get(uniformName), array);
    }

    public void uniform(String uniformName, IColor color){
        program.uniform(uniforms.get(uniformName), color.r(), color.g(), color.b(), color.a());
    }

    public void uniform(String uniformName, Texture texture){
        texture.bind(num_sampler2D);
        program.uniform(uniforms.get(uniformName), num_sampler2D);
        num_sampler2D++;
    }

    public void uniform(String uniformName, TextureArray textureArray){
        textureArray.bind(num_sampler2DArray);
        program.uniform(uniforms.get(uniformName), num_sampler2DArray);
        num_sampler2DArray++;
    }

    public void uniform(String uniformName, CubeMap cubeMap){
        cubeMap.bind(num_samplerCube);
        program.uniform(uniforms.get(uniformName), num_samplerCube);
        num_samplerCube++;
    }


    public void bindAttribute(int index, String name){
        program.bindAttributeLoc(index, name);
    }

    public void bindFragData(int index, String name){
        program.bindFragDataLoc(index, name);
    }


    public void uniformBlockBinding(String uniformName, int bindingPoint){
        final int index = program.getUniformBlockIndex(uniformName);
        if(index < 0)
            throw new RuntimeException("No uniform buffer called '" + uniformName + "'");
        program.uniformBlockBinding(index, bindingPoint);
    }

    
    public void bind(){
        program.bind();

        num_sampler2D = 0;
        num_samplerCube = 0;
        num_sampler2DArray = 0;
    }

    public static void unbind(){
        GlProgram.unbind();
    }


    @Override
    public void dispose(){
        program.dispose();
    }


}
