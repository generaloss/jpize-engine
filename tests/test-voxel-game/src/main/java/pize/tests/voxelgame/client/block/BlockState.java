package pize.tests.voxelgame.client.block;

public class BlockState{
    
    public static BlockProperties getProps(short state){
        return Blocks.fromID(getID(state));
    }
    
    public static BlockProperties getProps(byte id){
        return Blocks.fromID(id);
    }
    
    public static short getState(int id, byte extraData){
        return (short) ((id & 0xFF | (((short) extraData) << 8)));
    }
    
    public static short getState(int id){
        return getState(id, (byte) 0);
    }
    
    
    public static byte getID(short state){
        return (byte) (state & 0xFF);
    }
    
    public static byte getExtraData(short state){
        return (byte) (state >> 8);
    }

}
