package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.model.BlockTextureRegion;

import static megalul.projectvostok.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

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
