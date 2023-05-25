package pize.tests.particle;

import pize.Pize;
import pize.activity.ActivityListener;
import pize.graphics.texture.Pixmap;
import pize.graphics.util.Canvas;
import pize.io.glfw.Key;

public class Main implements ActivityListener{

    public static void main(String[] args){
        Pize.create("Noise", 1280, 720);
        Pize.run(new Main());
    }

    
    private Canvas canvas;
    
    private Pixmap map;
    private PerlinNoise noise;

    @Override
    public void init(){
        canvas = new Canvas();
        map = new Pixmap(64, 64);
        
        noise = new PerlinNoise();
        noise.setScale(16);
    }

    @Override
    public void render(){
        for(int i = 0; i < map.getWidth(); i++){
            for(int j = 0; j < map.getHeight(); j++){
                float color = (noise.get(i, j) + 1) / 2;
                
                map.setPixel(i, j, color, color, color, 1F);
            }
        }
        
        canvas.clear(0.7F, 0.6F, 0.9F, 1);
        canvas.drawPixmap(map, (Pize.getWidth() - Pize.getHeight()) / 2, 0, (float) Pize.getHeight() / map.getWidth(), (float) Pize.getHeight() / map.getHeight());
        canvas.render();
        
        Pize.window().setTitle("Noise [fps: " + Pize.getFPS() + "]");
        if(Pize.isDown(Key.ESCAPE))
            Pize.exit();
    }

    @Override
    public void resize(int width, int height){
        canvas.resize(width, height);
    }

    @Override
    public void dispose(){
        canvas.dispose();
    }


}
