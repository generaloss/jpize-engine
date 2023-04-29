package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.model.BlockTextureRegion;

public class Air extends BlockProperties{

    protected Air(int id){
        super(id);
    }

    @Override
    public final boolean isSolid(){
        return false;
    }
    
    @Override
    public final boolean isTransparent(){
        return true;
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