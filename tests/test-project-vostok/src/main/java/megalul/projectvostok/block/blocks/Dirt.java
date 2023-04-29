package megalul.projectvostok.block.blocks;

import pize.graphics.texture.Region;
import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.model.BlockTextureRegion;

import static megalul.projectvostok.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class Dirt extends BlockProperties{

    private final BlockTextureRegion region;
    
    protected Dirt(int id){
        super(id);
        
        region = new BlockTextureRegion(
            new Region(6 / 8F, 3 / 8F, 7 / 8F, 4 / 8F)
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
