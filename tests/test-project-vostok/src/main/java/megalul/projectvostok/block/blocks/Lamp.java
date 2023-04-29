package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.model.BlockTextureRegion;
import pize.graphics.texture.Region;

import static megalul.projectvostok.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class Lamp extends BlockProperties{
    
    private final BlockTextureRegion region;
    
    protected Lamp(int id){
        super(id);
        
        region = new BlockTextureRegion(new Region(6 / 8F, 0 / 8F, 7 / 8F, 1 / 8F));
    }
    
    @Override
    public final boolean isSolid(){
        return true;
    }
    
    @Override
    public final BlockTextureRegion getTextureRegion(){
        return region;
    }
    
    @Override
    public final int getLightLevel(){
        return MAX_LIGHT_LEVEL;
    }
    
    @Override
    public final int getOpacity(){
        return MAX_LIGHT_LEVEL;
    }
    
}
