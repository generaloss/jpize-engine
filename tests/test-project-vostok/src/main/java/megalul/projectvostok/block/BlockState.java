package megalul.projectvostok.block;

import megalul.projectvostok.block.blocks.Block;

public class BlockState{
    
    public static BlockProperties getProps(short state){
        return getType(state).properties;
    }
    
    public static Block getType(short state){
        return Block.fromID(getID(state));
    }

    public static byte getID(short state){
        return (byte) (state & 0xFF);
    }

    public static byte getExtraData(short state){
        return (byte) (state >> 8);
    }

    public static short getState(int id, byte extraData){
        return (short) ((id & 0xFF | (((short) extraData) << 8)));
    }
    
    public static short getState(int id){
        return getState(id, (byte) 0);
    }

}
