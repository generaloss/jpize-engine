package megalul.projectvostok.client.block.blocks;

import megalul.projectvostok.client.block.BlockProperties;
import megalul.projectvostok.client.block.model.BlockTextureRegion;
import pize.graphics.texture.Region;

import static megalul.projectvostok.clientserver.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class Stone extends BlockProperties{
    
    private final BlockTextureRegion region;
    
    protected Stone(int id){
        super(id);
        
        region = new BlockTextureRegion(
            new Region(6 / 8F, 2 / 8F, 7 / 8F, 3 / 8F)
        );
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
        return 0;
    }
    
    @Override
    public final int getOpacity(){
        return MAX_LIGHT_LEVEL;
    }
    
}
