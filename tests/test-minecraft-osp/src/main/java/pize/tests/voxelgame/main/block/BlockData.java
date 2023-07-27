package pize.tests.voxelgame.main.block;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.Blocks;

public class BlockData{
    
    public static BlockProperties getProps(short data){
        return Blocks.fromID(getID(data));
    }
    
    public static BlockProperties getProps(byte ID){
        return Blocks.fromID(ID);
    }

    public static short getData(BlockProperties block, byte extraData){
        return getData(block.getID(), extraData);
    }

    public static short getData(int id, byte extraData){
        return (short) ((id & 0xFF | (((short) extraData) << 8)));
    }

    public static short getData(BlockProperties block){
        return getData(block.getID());
    }
    
    public static short getData(int id){
        return getData(id, (byte) 0);
    }
    
    
    public static byte getID(short data){
        return (byte) (data & 0xFF);
    }
    
    public static byte getState(short data){
        return (byte) (data >> 8);
    }

}
