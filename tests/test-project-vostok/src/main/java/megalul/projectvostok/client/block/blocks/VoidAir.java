package megalul.projectvostok.client.block.blocks;

import megalul.projectvostok.client.block.BlockProperties;
import megalul.projectvostok.client.block.model.BlockTextureRegion;

import static megalul.projectvostok.clientserver.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

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
