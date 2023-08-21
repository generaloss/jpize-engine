package pize.tests;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.io.key.Key;
import pize.math.Maths;

public class NoiseTest extends AppAdapter{
    
    public static void main(String[] args){
        Pize.create("Noise", 720, 720);
        Pize.run(new NoiseTest());
    }
    
    
    private final TextureBatch batch;
    private final MyNoise noise;
    private final Texture mapTexture;
    
    public NoiseTest(){
        batch = new TextureBatch();
        noise = new MyNoise();
        
        final Pixmap pixmap = new Pixmap(2048, 2048);
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
        batch.draw(mapTexture, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.end();
        
        if(Key.ESCAPE.isDown())
            Pize.exit();
    }
    
}