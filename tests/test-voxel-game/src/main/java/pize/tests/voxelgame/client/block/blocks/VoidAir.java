package pize.tests.voxelgame.client.block.blocks;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockTextureRegion;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class VoidAir extends BlockProperties{
    
    protected VoidAir(int id){
        super(id);
    }
    
    @Override
    public final boolean isSolid(){
        return false;
    }
    
    @Override
    public final BlockTextureRegion getTextureRegion(){
        return null;
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
