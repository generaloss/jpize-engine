package test;

import jpize.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.util.batch.TextureBatch;
import jpize.util.math.FastNoise;

public class NoiseTest extends JpizeApplication{

    TextureBatch batch;
    Texture map;

    public void init(){
        batch = new TextureBatch();
        final PixmapRGBA pixmap = new PixmapRGBA(512, 512);

        final FastNoise noise = new FastNoise()
            .setNoiseType(FastNoise.NoiseType.OPEN_SIMPLEX_2)
            .setFrequency(0.01F)
            .setFractalType(FastNoise.FractalType.FBM)
            .setFractalGain(0.3F)
            .setFractalOctaves(10);

        for(int i = 0; i < pixmap.getWidth(); i++){
            for(int j = 0; j < pixmap.getHeight(); j++){

                final float gs = noise.getNorm(i, j);
                pixmap.setPixel(i, j, gs, gs, gs, 1F);
            }
        }

        map = new Texture(pixmap);
    }

    public void render(){
        Gl.clearColor(0.2, 0.2, 0.2);
        Gl.clearColorBuffer();

        batch.begin();
        batch.draw(map, 0, 0, Jpize.getHeight(), Jpize.getHeight());
        batch.end();
    }

    public void dispose(){
        batch.dispose();
        map.dispose();
    }

}
