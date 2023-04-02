package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.BlockState;

public enum Block{

    AIR(new Air(0)), // ID must be 0
    DIRT(new Dirt(1));
    
    
    public final int id;
    public final BlockProperties properties;
    private BlockState defaultState;

    Block(BlockProperties properties){
        this.id = properties.getID();
        this.properties = properties;
    }

    public BlockState getState(){
        if(defaultState == null)
            defaultState = new BlockState(this);
        
        return defaultState;
    }

    public static Block fromID(int id){
        return Block.values()[id];
    }

}
