package pize.graphics.util;

import pize.files.FileHandle;
import pize.graphics.camera.PerspectiveCamera;

public class BaseShader extends Shader{

    public BaseShader(){
        super(
            new FileHandle("shader/base/base.vert"),
            new FileHandle("shader/base/base.frag")
        );
    }

    public void setPerspective(PerspectiveCamera camera){
        setUniform("u_projection", camera.getProjection());
        setUniform("u_view", camera.getView());
    }

}
