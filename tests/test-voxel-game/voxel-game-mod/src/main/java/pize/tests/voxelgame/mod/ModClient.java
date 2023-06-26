package pize.tests.voxelgame.mod;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.tests.voxelgame.base.modification.api.ClientModInitializer;

public class ModClient implements ClientModInitializer{
    
    private TextureBatch batch;
    private Texture texture;
    
    @Override
    public void onInitializeClient(){
        System.out.println("[Voxel Game Mod]: client point initialized");
        
        batch = new TextureBatch();
        texture = new Texture(new Resource("icon.png", ModClient.class));
    }
    
    
    public void render(){
        batch.begin();
        batch.draw(texture, Pize.getWidth() - 20, Pize.getHeight() - 20, 20, 20);
        batch.end();
    }
    
}
