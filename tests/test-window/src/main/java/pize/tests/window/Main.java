package pize.tests.window;

import pize.Pize;
import pize.io.context.ContextAdapter;
import pize.gl.Gl;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.glfw.key.Key;
import pize.io.context.ContextBuilder;

public class Main extends ContextAdapter{
    
    public static void main(String[] args){
        ContextBuilder.newContext("Hello, Window!")
                .size(1080, 640)
                .create()
                .init(new Main());

        Pize.runContexts();
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
        batch.draw(texture, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.end();
        
        if(Key.ESCAPE.isPressed())
            Pize.exit();
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        texture.dispose();
    }
    
}
