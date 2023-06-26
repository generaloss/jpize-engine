package pize.tests.voxelgame.client.block.blocks;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;

public enum Block{
    
    AIR(new Air(0)),
    DIRT(new Dirt(1)),
    GRASS_BLOCK(new GrassBlock(2)),
    STONE(new Stone(3)),
    GLASS(new Glass(4)),
    OAK_LOG(new OakLog(5)),
    LAMP(new Lamp(6));
    
    
    public final int ID;
    public final BlockProperties properties;
    private final short defaultState;

    Block(BlockProperties properties){
        this.ID = properties.getID();
        this.properties = properties;
        defaultState = BlockState.getState(ID);
    }

    public final short getDefaultState(){
        return defaultState;
    }

    public static Block fromID(int id){
        for(Block block: values())
            if(block.ID == id)
                return block;
        
        return null;
    }

}
