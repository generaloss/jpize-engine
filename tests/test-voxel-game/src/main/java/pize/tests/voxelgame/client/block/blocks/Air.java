package pize.tests.voxelgame.client.block.blocks;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockTextureRegion;

public class Air extends BlockProperties{

    protected Air(int id){
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
        return 0;
    }

}