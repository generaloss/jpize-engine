package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;

public class Dirt extends BlockProperties{

    protected Dirt(int id){
        super(id);
    }

    @Override
    public boolean isSolid(){
        return true;
    }

}
