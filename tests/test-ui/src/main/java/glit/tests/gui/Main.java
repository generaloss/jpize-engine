package glit.tests.gui;

import glit.Glit;
import glit.context.ContextListener;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureRegion;
import glit.graphics.gl.Gl;
import glit.graphics.util.batch.TextureBatch;
import glit.gui.Align;
import glit.gui.LayoutType;
import glit.gui.components.ExpandType;
import glit.gui.components.NinePatchImage;
import glit.gui.components.Layout;
import glit.gui.components.RegionMesh;
import glit.gui.constraint.Constraint;
import glit.io.glfw.Key;

public class Main implements ContextListener{

    public static void main(String[] args){
        Glit.create("GUI", 480, 360);
        Glit.window().setIcon("icon.png");
        Glit.init(new Main());
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
        if(Glit.isDown(Key.ESCAPE))
            Glit.exit();
        Gl.clearBufferColor();
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