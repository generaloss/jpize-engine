package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;

public class GrassBlock extends BlockProperties{

    protected GrassBlock(int id){
        super(id);
    }

    @Override
    public boolean isSolid(){
        return true;
    }

}