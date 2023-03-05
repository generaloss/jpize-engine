package glit.graphics.util;

import glit.files.FileHandle;
import glit.graphics.texture.Texture;

public class ScreenQuadShader{

    private static ScreenQuadShader instance;

    private final Shader shader;

    public ScreenQuadShader(){
        shader = new Shader(new FileHandle("shader/screen/screen.vert"), new FileHandle("shader/screen/screen.frag"));
    }

    public static void use(Texture texture){
        if(instance == null)
            instance = new ScreenQuadShader();

        instance.shader.bind();
        instance.shader.setUniform("u_texture", texture);
    }

    private static void dispose(){
        if(instance != null)
            instance.shader.dispose();
    }

}
