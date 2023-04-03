package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.BlockState;

public enum Block{

    AIR(new Air(0)), // ID must be 0
    DIRT(new Dirt(1));
    
    
    public final int id;
    public final BlockProperties properties;
    private final short defaultState;

    Block(BlockProperties properties){
        this.id = properties.getID();
        this.properties = properties;
        defaultState = BlockState.getState(id);
    }

    public short getState(){
        return defaultState;
    }

    public static Block fromID(int id){
        return Block.values()[id];
    }

}
