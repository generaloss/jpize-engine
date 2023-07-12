package pize.graphics.util;

import pize.files.Resource;
import pize.graphics.texture.Texture;

public class ScreenQuadShader{

    private static ScreenQuadShader instance;

    private final Shader shader;

    public ScreenQuadShader(){
        shader = new Shader(new Resource("shader/screen/screen.vert"), new Resource("shader/screen/screen.frag"));
    }

    public static void use(Texture texture){
        if(instance == null)
            instance = new ScreenQuadShader();

        instance.shader.bind();
        instance.shader.setUniform("u_texture", texture);
    }

    private static void dispose(){ // Invoked from Context
        if(instance != null)
            instance.shader.dispose();
    }

}
