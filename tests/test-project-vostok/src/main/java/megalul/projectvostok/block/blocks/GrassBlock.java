package megalul.projectvostok.block.blocks;

import pize.graphics.texture.Region;
import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.model.BlockTextureRegion;

import static megalul.projectvostok.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class GrassBlock extends BlockProperties{
    
    private final BlockTextureRegion region;
    
    protected GrassBlock(int id){
        super(id);
        
        region = new BlockTextureRegion(
            new Region(5 / 8F, 1 / 8F, 6 / 8F, 2 / 8F),
            Block.DIRT.properties.getTextureRegion().getPx(),
            new Region(4 / 8F, 1 / 8F, 5 / 8F, 2 / 8F)
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
