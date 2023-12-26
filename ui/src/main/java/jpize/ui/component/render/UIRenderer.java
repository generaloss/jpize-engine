package jpize.ui.component.render;

import jpize.graphics.util.Shader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.util.Disposable;
import jpize.util.Resizable;
import jpize.util.file.Resource;

public class UIRenderer implements Disposable, Resizable{

    private final Shader shader;
    private final TextureBatch batch;
    private final UIComponentBuffer stencil;

    public UIRenderer(){
        this.shader = new Shader(Resource.internal("ui.vert"), Resource.internal("ui.frag"));
        setCornerSoftness(0.5F);
        setBorderSoftness(0.5F);

        this.batch = new TextureBatch();
        this.batch.useShader(shader);

        this.stencil = new UIComponentBuffer();
    }

    public void setCornerSoftness(float softness){
        this.shader.setUniform("u_cornerSoftness", softness);
    }

    public void setBorderSoftness(float softness){
        this.shader.setUniform("u_borderSoftness", softness);
    }

    public void rect(float x, float y, float width, float height, float cornerRadius, float borderSize, Color borderColor){
        this.shader.bind();
        this.shader.setUniform("u_cornerRadius", cornerRadius);
        this.shader.setUniform("u_borderSize", borderSize);
        this.shader.setUniform("u_borderColor", borderColor);
        this.shader.setUniform("u_center", x + width / 2, y + height / 2);
        this.shader.setUniform("u_size", width, height);
    }

    public TextureBatch batch(){
        return batch;
    }

    public UIComponentBuffer stencil(){
        return stencil;
    }

    public void begin(){
        stencil.clear();
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

    @Override
    public void resize(int width, int height){
        stencil.resize(width, height);
    }

}
