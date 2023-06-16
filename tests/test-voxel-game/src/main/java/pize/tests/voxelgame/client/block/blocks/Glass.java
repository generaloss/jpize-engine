package pize.tests.voxelgame.client.block.blocks;

import pize.physic.BoundingBox;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockShape;
import pize.tests.voxelgame.client.block.model.BlockTextureRegion;
import pize.graphics.texture.Region;

public class Glass extends BlockProperties{
    
    private final BlockTextureRegion region;
    
    protected Glass(int id){
        super(id);
        
        region = new BlockTextureRegion(
            new Region(0 / 8F, 2 / 8F, 1 / 8F, 3 / 8F)
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
        return 4;
    }
    
    @Override
    public BlockShape getShape(){
        return new BlockShape(new BoundingBox(0, 0, 0, 1, 1, 1));
    }
    
}