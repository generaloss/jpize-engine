package pize.tests.gui;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.gl.Gl;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.components.ExpandType;
import pize.gui.components.Layout;
import pize.gui.components.NinePatchImage;
import pize.gui.components.RegionMesh;
import pize.gui.constraint.Constraint;
import pize.io.glfw.Key;

public class Main extends AppAdapter{

    public static void main(String[] args){
        Pize.create("Test - UI", 480, 360);
        Pize.window().setIcon("icon.png");
        Pize.run(new Main());
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
            Pize.exit();
        
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