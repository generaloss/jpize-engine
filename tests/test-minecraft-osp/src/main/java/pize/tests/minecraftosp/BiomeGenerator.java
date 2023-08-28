package pize.tests.minecraftosp;

import pize.Pize;
import pize.io.context.ContextAdapter;
import pize.gl.Gl;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.io.context.ContextBuilder;
import pize.math.Maths;
import pize.math.function.FastNoiseLite;

public class BiomeGenerator extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Biome Generator")
                .size(1280, 720)
                .create()
                .init(new BiomeGenerator());

        Pize.runContexts();
    }

    private final TextureBatch batch;
    private Texture mapTexture;
    
    private final FastNoiseLite
        continentalnessNoise, erosionNoise, peaksValleysNoise, temperatureNoise, humidityNoise, randomNoise;

    public BiomeGenerator(){
        batch = new TextureBatch();

        continentalnessNoise = new FastNoiseLite();
        erosionNoise = new FastNoiseLite();
        peaksValleysNoise = new FastNoiseLite();
        temperatureNoise = new FastNoiseLite();
        humidityNoise = new FastNoiseLite();
        randomNoise = new FastNoiseLite();

        continentalnessNoise.setFrequency(0.002F);
        continentalnessNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        continentalnessNoise.setFractalOctaves(7);

        erosionNoise.setFrequency(0.002F);
        erosionNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        erosionNoise.setFractalOctaves(5);
        randomNoise.setFrequency(1);
    }

    
    public void init(){
        final Pixmap map = new Pixmap(1024, 1024);
        
        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y < map.getHeight(); y++){
                final float continentalness = continentalnessNoise.getNoise(x, y);
                final float erosion = erosionNoise.getNoise(x, y);
                
                
                final float grayscale = Maths.round(Maths.map(erosion, -0.55 * Maths.Sqrt2, 0.55 * Maths.Sqrt2, 0, 1) * 5) / 5F;
                
                map.setPixel(x, y, grayscale, grayscale, grayscale, 1);
            }
        }
        
        mapTexture = new Texture(map);
    }
    
    
    public void render(){
        Gl.clearColorBuffer();
        batch.begin();
        batch.draw(mapTexture, 0, 0, 1280, 1280);
        batch.end();
    }
    
}
