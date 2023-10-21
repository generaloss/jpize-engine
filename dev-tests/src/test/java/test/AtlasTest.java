package test;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.graphics.texture.atlas.TextureAtlas;
import jpize.gl.Gl;
import jpize.graphics.util.batch.TextureBatch;

public class AtlasTest extends JpizeApplication{
    
    TextureBatch batch;
    TextureAtlas<Integer> atlas;
    
    public void init(){
        batch = new TextureBatch();
        atlas = new TextureAtlas<>();
        
        for(int i = 1; i <= 25; i++)
            atlas.put(i, "texture" + i + ".png");
        
        atlas.generate(128, 128, 1);
    }
    
    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.4, 0.5, 0.7);
        
        batch.begin();
        batch.draw(atlas.getTexture(), 0, 0, Jpize.getWidth(), Jpize.getHeight());
        batch.end();
    }
    
}
