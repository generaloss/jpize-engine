package jpize.tests;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.glfw.key.Key;
import jpize.io.context.ContextBuilder;
import jpize.math.Maths;

public class NoiseTest extends JpizeApplication{
    
    public static void main(String[] args){
        ContextBuilder.newContext("Noise")
                .size(720, 720)
                .register()
                .setAdapter(new NoiseTest());

        Jpize.runContexts();
    }
    
    
    private final TextureBatch batch;
    private final MyNoise noise;
    private final Texture mapTexture;
    
    public NoiseTest(){
        batch = new TextureBatch();
        noise = new MyNoise();
        
        final PixmapRGBA pixmap = new PixmapRGBA(2048, 2048);
        for(int x = 0; x < pixmap.getWidth(); x++){
            for(int y = 0; y < pixmap.getHeight(); y++){
                float grayscale = OpenSimplex2S.noise2_ImproveX(22854, x / 256F, y / 256F);
                grayscale = Maths.map(grayscale, -1, 1, 0, 1);
                System.out.println(grayscale);
                pixmap.setPixel(x, y, grayscale, grayscale, grayscale, 1);
            }
        }
        
        mapTexture = new Texture(pixmap);
    }
    
    public void render(){
        batch.begin();
        batch.draw(mapTexture, 0, 0, Jpize.getWidth(), Jpize.getHeight());
        batch.end();
        
        if(Key.ESCAPE.isDown())
            Jpize.exit();
    }
    
}