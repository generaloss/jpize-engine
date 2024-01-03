package jpize.graphics.util;

import jpize.util.file.Resource;
import jpize.graphics.texture.Texture;

public class ScreenQuadShader{

    private static ScreenQuadShader instance;

    private final Shader shader;

    public ScreenQuadShader(){
        shader = new Shader(Resource.internal("shader/screen/screen.vert"), Resource.internal("shader/screen/screen.frag"));
    }

    public static void use(Texture texture){
        if(instance == null)
            instance = new ScreenQuadShader();

        instance.shader.bind();
        instance.shader.uniform("u_texture", texture);
    }

    private static void dispose(){ // Calls from ContextManager
        if(instance != null)
            instance.shader.dispose();
    }

}
