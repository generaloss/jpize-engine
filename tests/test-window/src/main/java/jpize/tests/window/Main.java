package jpize.tests.window;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.gl.Gl;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.glfw.key.Key;
import jpize.io.context.ContextBuilder;

public class Main extends JpizeApplication{
    
    public static void main(String[] args){
        ContextBuilder.newContext("Hello, Window!")
                .size(1080, 640)
                .register()
                .setAdapter(new Main());

        Jpize.runContexts();
    }
    
    
    private TextureBatch batch;
    private Texture texture;
    
    @Override
    public void init(){
        batch = new TextureBatch();
        texture = new Texture("wallpaper-19.jpg");
    }
    
    
    @Override
    public void render(){
        Gl.clearColorBuffer();
        batch.begin();
        batch.draw(texture, 0, 0, Jpize.getWidth(), Jpize.getHeight());
        batch.end();
        
        if(Key.ESCAPE.isPressed())
            Jpize.exit();
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        texture.dispose();
    }
    
}
