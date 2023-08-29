package pize.devtests;

import pize.Jize;
import pize.io.context.ContextAdapter;
import pize.graphics.texture.atlas.TextureAtlas;
import pize.gl.Gl;
import pize.graphics.util.batch.TextureBatch;

public class AtlasTest extends ContextAdapter{
    
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
        batch.draw(atlas.getTexture(), 0, 0, Jize.getWidth(), Jize.getHeight());
        batch.end();
    }
    
}
