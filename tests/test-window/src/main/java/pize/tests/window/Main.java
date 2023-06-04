package pize.tests.window;

import pize.Pize;
import pize.activity.ActivityListener;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;

public class Main implements ActivityListener{
    
    public static void main(String[] args){
        Pize.create("Window", 1080, 640);
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
        batch.begin();
        batch.draw(texture, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.end();
    }
    
    @Override
    public void resize(int width, int height){ }
    
    @Override
    public void dispose(){
        batch.dispose();
        texture.dispose();
    }
    
}
