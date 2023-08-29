package jpize.tests.gui;

import jpize.Jpize;
import jpize.io.context.ContextAdapter;
import jpize.gl.Gl;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureRegion;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.Align;
import jpize.gui.LayoutType;
import jpize.gui.components.ExpandType;
import jpize.gui.components.Layout;
import jpize.gui.components.NinePatchImage;
import jpize.gui.components.RegionMesh;
import jpize.gui.constraint.Constraint;
import jpize.glfw.key.Key;
import jpize.io.context.ContextBuilder;

public class Main extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Test - UI")
                .icon("icon.png")
                .create()
                .init(new Main());

        Jpize.runContexts();
    }
    
    private TextureBatch batch;
    private Texture texture;
    private Layout layout;

    @Override
    public void init(){
        batch = new TextureBatch();

        texture = new Texture("widgets.png");
        final TextureRegion buttonTextureRegion = new TextureRegion(texture, 0, 66, 200, 20);
        final RegionMesh regionMesh = new RegionMesh(0,0, 2,2, 198,17, 200,20);

        // UI
        layout = new Layout();
        layout.setLayoutType(LayoutType.HORIZONTAL);
        layout.alignItems(Align.CENTER);

        final NinePatchImage button = new NinePatchImage(buttonTextureRegion, regionMesh);
        button.setExpandType(ExpandType.HORIZONTAL);
        button.setSize(Constraint.relative(0.333), Constraint.relative(0.333));
        layout.put("button", button);
    }

    @Override
    public void render(){
        if(Key.ESCAPE.isDown())
            Jpize.exit();
        
        Gl.clearColorBuffer();
        Gl.clearColor(0.08, 0.11, 0.15, 1);
        batch.begin();
        layout.render(batch); // render layout
        batch.end();
    }

    @Override
    public void dispose(){
        batch.dispose();
        texture.dispose();
    }

}