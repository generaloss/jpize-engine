package pize.tests.window;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.gl.Gl;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;

public class Main extends AppAdapter{
    
    public static void main(String[] args){
        Pize.create("Hello, Window!", 1080, 640);
        Pize.run(new Main());
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
