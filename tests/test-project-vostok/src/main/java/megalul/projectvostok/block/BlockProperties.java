package megalul.projectvostok.block;

import megalul.projectvostok.block.blocks.Block;

public abstract class BlockProperties{

    private final int id;

    protected BlockProperties(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public boolean isEmpty(){
        return id == Block.AIR.id;
    }

    public abstract boolean isSolid();

}
