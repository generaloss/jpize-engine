package megalul.projectvostok.block.blocks;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.BlockState;

public enum Block{

    AIR(new Air(0)),
    DIRT(new Dirt(1)),
    GRASS_BLOCK(new GrassBlock(2));


    public final int id;
    public final BlockProperties properties;
    private final BlockState defaultState;

    Block(BlockProperties properties){
        this.id = properties.getID();
        this.properties = properties;

        defaultState = new BlockState(this);
    }

    public BlockState getState(){
        return defaultState;
    }

    public static Block fromID(int id){
        return Block.values()[id];
    }

}
