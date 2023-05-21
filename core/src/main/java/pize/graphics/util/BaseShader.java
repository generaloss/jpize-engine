package pize.graphics.util;

import pize.files.Resource;
import pize.graphics.camera.PerspectiveCamera;

public class BaseShader extends Shader{

    public BaseShader(){
        super(
            new Resource("shader/base/base.vert"),
            new Resource("shader/base/base.frag")
        );
    }

    public void setPerspective(PerspectiveCamera camera){
        setUniform("u_projection", camera.getProjection());
        setUniform("u_view", camera.getView());
    }

}
