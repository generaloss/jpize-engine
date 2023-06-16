package pize.tests.voxelgame.client.block.blocks;

import pize.graphics.texture.Region;
import pize.physic.BoundingBox;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockShape;
import pize.tests.voxelgame.client.block.model.BlockTextureRegion;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class OakLog extends BlockProperties{
    
    private final BlockTextureRegion region;
    
    protected OakLog(int id){
        super(id);
        
        region = new BlockTextureRegion(
            new Region(2 / 8F, 5 / 8F, 3 / 8F, 6 / 8F),
            new Region(2 / 8F, 6 / 8F, 3 / 8F, 7 / 8F)
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
    
    @Override
    public BlockShape getShape(){
        return new BlockShape(new BoundingBox(0, 0, 0, 1, 1, 1));
    }
    
}