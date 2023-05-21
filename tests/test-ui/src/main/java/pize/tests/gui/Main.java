package pize.tests.gui;

import pize.Pize;
import pize.context.ContextListener;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.gl.Gl;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.components.ExpandType;
import pize.gui.components.NinePatchImage;
import pize.gui.components.Layout;
import pize.gui.components.RegionMesh;
import pize.gui.constraint.Constraint;
import pize.io.glfw.Key;

public class Main implements ContextListener{

    public static void main(String[] args){
        Pize.create("GUI", 480, 360);
        Pize.window().setIcon("icon.png");
        Pize.run(new Main());
    }


    private TextureBatch batch;

    private Layout layout;

    private Texture texture;

    @Override
    public void init(){
        batch = new TextureBatch();

        texture = new Texture("widgets.png");
        TextureRegion buttonTextureRegion = new TextureRegion(texture, 0, 66, 200, 20);
        RegionMesh regionMesh = new RegionMesh(0,0, 2,2, 198,17, 200,20);

        // GUI

        layout = new Layout();
        layout.setLayoutType(LayoutType.HORIZONTAL);
        layout.alignItems(Align.CENTER);

        NinePatchImage button = new NinePatchImage(buttonTextureRegion, regionMesh);
        button.setExpandType(ExpandType.HORIZONTAL);
        button.setSize(Constraint.relative(0.333), Constraint.relative(0.333));
        layout.put("button", button);
    }

    @Override
    public void render(){
        if(Pize.isDown(Key.ESCAPE))
            Pize.exit();
        Gl.clearColorBuffer();
        Gl.clearColor(0.08, 0.11, 0.15, 1);

        batch.begin();
        layout.render(batch);
        batch.end();
    }

    @Override
    public void resize(int w,int h){ }

    @Override
    public void dispose(){
        batch.dispose();
        texture.dispose();
    }

}