package test.devs;

import jpize.graphics.mesh.IMesh;
import jpize.graphics.util.BaseShader;

public class MeshInstance{

    private final IMesh mesh;
    private final BaseShader shader;

    public MeshInstance(IMesh mesh, BaseShader shader){
        this.mesh = mesh;
        this.shader = shader;
    }

    public IMesh getMesh(){
        return mesh;
    }

    public BaseShader getShader(){
        return shader;
    }

}
