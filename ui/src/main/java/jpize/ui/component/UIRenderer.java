package jpize.ui.component;

import jpize.app.Disposable;
import jpize.app.Resizable;
import jpize.graphics.camera.OrthographicCamera;
import jpize.graphics.util.Shader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.util.color.Color;
import jpize.util.res.Resource;

public class UIRenderer implements Disposable, Resizable{

    private final Shader shader;
    private final TextureBatch batch;
    private final OrthographicCamera camera;

    public UIRenderer(){
        this.batch = new TextureBatch();
        this.shader = new Shader(Resource.internal("ui.vert"), Resource.internal("ui.frag"));
        this.batch.useShader(null);
        
        this.camera = new OrthographicCamera();

        setCornerSoftness(0.5F);
        setBorderSoftness(0.5F);
    }

    public void setCornerSoftness(float softness){
        this.shader.uniform("u_cornerSoftness", softness);
    }

    public void setBorderSoftness(float softness){
        this.shader.uniform("u_borderSoftness", softness);
    }

    public void beginRect(float x, float y, float width, float height, float cornerRadius, float borderSize, Color borderColor){
        batch.end();
        batch.useShader(shader);
        shader.bind();
        shader.uniform("u_cornerRadius", cornerRadius);
        shader.uniform("u_borderSize", borderSize);
        shader.uniform("u_borderColor", borderColor);
        shader.uniform("u_center", x + width / 2, y + height / 2);
        shader.uniform("u_size", width, height);
    }

    public void endRect(){
        batch.end();
        batch.useShader(null);
    }

    public void beginScissor(UIComponent component){
        final UIComponentCache cache = component.cache();

        final UIComponent parent = cache.parent;
        final int parentIndex = (parent == null) ? -1 : Math.abs(parent.hashCode());

        final float x = cache.x + cache.marginLeft;
        final float y = cache.y + cache.marginBottom ;
        final float width  = cache.containerWidth ;
        final float height = cache.containerHeight;

        batch.getScissor().begin(Math.abs(component.hashCode()), x, y, width, height, parentIndex);
    }

    public void endScissor(UIComponent component){
        batch.getScissor().end(Math.abs(component.hashCode()));
    }

    public TextureBatch batch(){
        return batch;
    }

    public void begin(){
        camera.update();
        batch.begin(camera);
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
        camera.resize(width, height);
    }

}
