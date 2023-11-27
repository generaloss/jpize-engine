package jpize.ui.component;

import jpize.Jpize;
import jpize.graphics.util.Shader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.sdl.renderer.SdlRenderer;
import jpize.sdl.renderer.SdlRendererFlags;
import jpize.util.Disposable;
import jpize.util.file.Resource;

public class UIRenderContext implements Disposable{

    private final Shader shader;
    private final TextureBatch batch;

    public UIRenderContext(){
        this.shader = new Shader(Resource.internal("ui.vert"), Resource.internal("ui.frag"));

        this.batch = new TextureBatch();
        this.batch.useShader(shader);
    }

    public void rect(float x, float y, float width, float height, float radius){
        this.shader.bind();
        this.shader.setUniform("u_cornerRadius", radius);
        this.shader.setUniform("u_pos", x, y);
        this.shader.setUniform("u_size", width, height);
    }

    public TextureBatch batch(){
        return batch;
    }

    public void begin(){
        batch.begin();
    }

    public void end(){
        batch.end();
    }

    @Override
    public void dispose(){
        shader.dispose();
        batch.dispose();
    }

}
