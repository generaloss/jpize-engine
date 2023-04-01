package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;

public class Air extends BlockProperties{

    protected Air(int id){
        super(id);
    }

    @Override
    public boolean isSolid(){
        return false;
    }

}