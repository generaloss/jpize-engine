package pize.tests.minecraft.run;

import pize.Pize;
import pize.gl.Gl;
import pize.glfw.key.Key;
import pize.graphics.camera.CenteredOrthographicCamera;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.graphics.util.color.Color;
import pize.io.context.ContextAdapter;
import pize.io.context.ContextBuilder;
import pize.math.Maths;
import pize.math.function.FastNoiseLite;

public class BiomeGeneratorTest extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Minecraft")
                .size(925, 640)
                .icon("icon.png")
                .create()
                .init(new BiomeGeneratorTest());
        Pize.runContexts();
    }

    private TextureBatch batch;
    private CenteredOrthographicCamera camera;
    private float scale = 1, scaleMul = 1;
    private Texture mapTexture, cellTexture;
    private float SIZE;

    @Override
    public void init(){
        SIZE = Pize.getHeight();

        batch = new TextureBatch();
        camera = new CenteredOrthographicCamera();
        camera.getPosition().add(SIZE / 2F);
        generate(16 * 100);

        Pixmap cellPixmap = new Pixmap(16, 16);
        cellPixmap.clear(1, 1, 1, 1F);
        cellPixmap.fill(1, 1, 14, 14, 0, 0, 0, 0F);
        cellTexture = new Texture(cellPixmap);
    }

    @Override
    public void render(){
        Pize.window().setTitle("Minecraft (fps: " + Pize.getFPS() + ")");

        if(Key.ESCAPE.isDown())
            Pize.exit();
        if(Key.F11.isDown())
            Pize.window().toggleFullscreen();

        final float scroll = Pize.mouse().getScroll();
        if(scroll > 0)
            scaleMul += 0.01F * scroll;
        else if(scroll < 0)
            scaleMul += 0.01F * scroll;

        scaleMul = (scaleMul - 1) * 0.93F + 1;
        System.out.println(scaleMul);
        scale *= scaleMul;
        camera.setScale(scale);
        camera.update();

        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.6, 1);
        batch.begin(camera);
        batch.draw(mapTexture, 0, 0, SIZE, SIZE);
        float map16PixelSize = SIZE / mapTexture.getHeight() * 16;
        batch.draw(cellTexture,
                Maths.floor(((Pize.getX() + camera.getX()) - camera.getHalfWidth() ) / map16PixelSize) * map16PixelSize,
                Maths.floor(((Pize.getY() + camera.getY()) - camera.getHalfHeight()) / map16PixelSize) * map16PixelSize,
                map16PixelSize,
                map16PixelSize
        );
        batch.end();
    }

    @Override
    public void resize(int width, int height){
        camera.resize(width, height);
    }
    
    @Override
    public void dispose(){
        mapTexture.dispose();
        cellTexture.dispose();
        batch.dispose();
    }


    private void generate(int size){
        Pixmap mapPixmap = new Pixmap(size, size);
        mapPixmap.clear(1F, 1F, 1F, 1F);

        FastNoiseLite continentalnessNoise = new FastNoiseLite();
        continentalnessNoise.setFrequency(0.002F);
        continentalnessNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        continentalnessNoise.setFractalOctaves(7);

        FastNoiseLite riverNoise = new FastNoiseLite();
        riverNoise.setNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        riverNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        riverNoise.setSeed((int) Maths.randomSeed(4));
        riverNoise.setFractalOctaves(16);
        riverNoise.setFrequency(0.003F);

        FastNoiseLite temperatureNoise = new FastNoiseLite();
        temperatureNoise.setNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        temperatureNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        temperatureNoise.setSeed((int) Maths.randomSeed(4));
        temperatureNoise.setFrequency(0.002F);
        temperatureNoise.setFractalOctaves(8);

        for(int x = 0; x < mapPixmap.getWidth(); x++){
            for(int z = 0; z < mapPixmap.getHeight(); z++){
                float temperature = temperatureNoise.getNoise(x, z) / 2 + 0.5F;
                //float longHeight = periodicHeightMapNoise.getNoise(x,z);
                //longHeight = longHeight / Mathc.pow(1 - longHeight,-1.2);
                final float height = continentalnessNoise.getNoise(x, z) / 2 + 0.5F;// + longHeight;
                final float oceanLevel = 0.43F;
                final float humidity = 1 - height;
                final float river = riverNoise.getNoise(x, z) / 2 + 0.5F;

                final Color color = new Color(0, 0, 0, 0F);

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