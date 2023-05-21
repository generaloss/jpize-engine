package megalul.projectvostok.client.block.blocks;

import pize.graphics.texture.Region;
import megalul.projectvostok.client.block.BlockProperties;
import megalul.projectvostok.client.block.model.BlockTextureRegion;

import static megalul.projectvostok.clientserver.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

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
