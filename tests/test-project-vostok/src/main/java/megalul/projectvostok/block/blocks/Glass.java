package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.model.BlockTextureRegion;
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
    
}