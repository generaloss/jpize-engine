package glit.tests.minecraft.client.run;

import glit.Glit;
import glit.context.ContextListener;
import glit.graphics.texture.Pixmap;
import glit.graphics.texture.Texture;
import glit.graphics.util.color.Color;
import glit.graphics.gl.Gl;
import glit.graphics.util.batch.TextureBatch;
import glit.io.glfw.Key;
import glit.math.Maths;
import glit.math.function.FastNoiseLite;

public class BiomeGeneratorTest implements ContextListener{
    private TextureBatch batch;
    private Texture mapTexture, cellTexture;

    @Override
    public void init(){
        batch = new TextureBatch();
        generate(16 * 100);

        Pixmap cellPixmap = new Pixmap(16, 16);
        cellPixmap.clear(1, 1, 1, 1F);
        cellPixmap.fill(1, 1, 14, 14, 0, 0, 0, 0F);
        cellTexture = new Texture(cellPixmap);
    }

    @Override
    public void render(){
        Glit.window().setTitle("Minecraft (fps: " + Glit.getFps() + ")");

        if(Glit.isDown(Key.ESCAPE))
            Glit.exit();
        if(Glit.isDown(Key.F11))
            Glit.window().toggleFullscreen();
        if(Glit.isDown(Key.V))
            Glit.window().toggleVsync();

        Gl.clearBufferColor();
        Gl.clearColor(0.4, 0.6, 1);
        batch.begin();

        batch.draw(mapTexture, 0, 0, Glit.getHeight(), Glit.getHeight());
        float pixel16 = Glit.getHeight() / (float) mapTexture.getHeight() * 16;
        batch.draw(cellTexture, Maths.floor(Glit.getX() / pixel16) * pixel16, Maths.floor(Glit.getY() / pixel16) * pixel16, pixel16, pixel16);
        batch.end();
    }

    @Override
    public void resize(int w, int h){ }

    @Override
    public void dispose(){
        mapTexture.dispose();
        cellTexture.dispose();
        batch.dispose();
    }


    private void generate(int size){
        Pixmap mapPixmap = new Pixmap(size, size);
        mapPixmap.clear(1F, 1F, 1F, 1F);

        FastNoiseLite heightMapNoise = new FastNoiseLite();
        heightMapNoise.setNoiseType(FastNoiseLite.NoiseType.Perlin);
        heightMapNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        heightMapNoise.setSeed((int) Maths.randomSeed(4));
        heightMapNoise.setFractalOctaves(8);
        heightMapNoise.setFrequency(0.002F);
        heightMapNoise.SetRotationType3D(FastNoiseLite.RotationType3D.ImproveXZPlanes);

        FastNoiseLite riverNoise = new FastNoiseLite();
        riverNoise.setNoiseType(FastNoiseLite.NoiseType.Perlin);
        riverNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        riverNoise.setSeed((int) Maths.randomSeed(4));
        riverNoise.setFractalOctaves(16);
        riverNoise.setFrequency(0.003F);

        FastNoiseLite temperatureNoise = new FastNoiseLite();
        temperatureNoise.setNoiseType(FastNoiseLite.NoiseType.Perlin);
        temperatureNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        temperatureNoise.setSeed((int) Maths.randomSeed(4));
        temperatureNoise.setFrequency(0.002F);
        temperatureNoise.setFractalOctaves(8);

        for(int x = 0; x < mapPixmap.getWidth(); x++){
            for(int z = 0; z < mapPixmap.getHeight(); z++){
                float temperature = temperatureNoise.getNoise(x, z) / 2 + 0.5F;
                //float longHeight = periodicHeightMapNoise.getNoise(x,z);
                //longHeight = longHeight / Mathc.pow(1 - longHeight,-1.2);
                float height = heightMapNoise.getNoise(x, z) / 2 + 0.5F;// + longHeight;
                float oceanLevel = 0.43F;
                float humidity = 1 - height;
                float river = riverNoise.getNoise(x, z) / 2 + 0.5F;

                Color color = new Color(0, 0, 0, 0F);

                if(height <= oceanLevel){
                    if(temperature > 0.4)
                        color.set(0.1F, 0.3F, 1F, 1F); // ocean
                    else
                        color.set(0.6F, 0.8F, 1F, 1F); // ice ocean
                }else if(river > 0.49 && river < 0.51){
                    if(temperature > 0.4)
                        color.set(0.2F, 0.4F, 0.9F, 1F); // river
                    else
                        color.set(0.7F, 0.8F, 0.9F, 1F); // ice river
                }else{
                    if(humidity > 0.55){
                        if(temperature > 0.4)
                            color.set(1, 1, 0, 1F); // beach
                        else
                            color.set(1F, 1F, 0.5F, 1F); // snowy beach
                    }else if(humidity > 0.35){
                        if(temperature > 0.8)
                            color.set(0.4F, 0.6F, 0F, 1F); // savanna
                        else if(temperature > 0.52)
                            color.set(0F, 0.8F, 0.2F, 1F); // normal
                        else if(temperature > 0.4)
                            color.set(0.1F, 0.6F, 0.2F, 1F); // taiga
                        else
                            color.set(1F, 1F, 1F, 1F); // snowy taiga
                    }else{
                        if(temperature > 0.6)
                            color.set(1, 1, 0, 1F); // desert
                        else
                            color.set(0.6F, 0.8F, 0.6F, 1F); // windswept hills
                    }
                }
                mapPixmap.setPixel(x, z, color);
            }
        }

        mapTexture = new Texture(mapPixmap);
    }

}