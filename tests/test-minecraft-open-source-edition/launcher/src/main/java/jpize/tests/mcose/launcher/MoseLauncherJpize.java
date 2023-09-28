package jpize.tests.mcose.launcher;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.postprocess.effects.GaussianBlur;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.components.Layout;
import jpize.io.context.JpizeApplication;

public class MoseLauncherJpize extends JpizeApplication{

    private final MoseClient client;

    private final TextureBatch batch;
    private final GaussianBlur blur;
    private final Texture background;
    private final Layout layout;

    public MoseLauncherJpize(){
        this.client = new MoseClient();
        this.batch = new TextureBatch();

        this.background = new Texture("background.png");

        this.blur = new GaussianBlur(5);

        this.layout = new Layout();
        buildGui();
    }

    private void buildGui(){

    }

    public void update(){
        if(Key.ESCAPE.isDown())
            Jpize.exit();

        if(Key.L.isDown()){
            Jpize.window().hide();
            client.launch();
            Jpize.window().show();
        }
    }

    public void render(){
        Gl.clearColorBuffer();

        // Blurred background
        blur.begin();
        batch.begin();
        batch.draw(background, 0, 0, Jpize.getWidth(), Jpize.getHeight());
        batch.end();
        blur.end();

        // Gui
        batch.begin();
        layout.render(batch);
        batch.end();
    }

    public void resize(int width, int height){
        blur.resize(width, height);
    }

    public void dispose(){
        background.dispose();
    }

}