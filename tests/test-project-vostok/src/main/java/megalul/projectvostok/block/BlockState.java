package megalul.projectvostok.block;

import megalul.projectvostok.block.blocks.Block;

public class BlockState{

    public static final BlockState AIR = new BlockState(Block.AIR);


    private final short state; // 8bits=data, 8bits=id
    private final Block type;
    private final byte extraData;

    public BlockState(short state){
        this.state = state;
        type = Block.fromID(getIDFromState(state));
        extraData = getExtraDataFromState(state);
    }

    public BlockState(Block type, byte extraData){
        this.type = type;
        this.extraData = extraData;
        state = getState(type.id, extraData);
    }

    public BlockState(Block type){
        this.type = type;
        this.extraData = (byte) 0;
        state = getState(type.id, extraData);
    }

    public short getState(){
        return state;
    }


    public Block getType(){
        return type;
    }

    public byte getExtraData(){
        return extraData;
    }

    public int getID(){
        return type.id;
    }

    public BlockProperties getProp(){
        return type.properties;
    }


    public static byte getIDFromState(short state){
        return (byte) (state & 0xFF);
    }

    public static byte getExtraDataFromState(short state){
        return (byte) (state >> 8);
    }

    public static short getState(int id, byte extraData){
        return (short) ((id & 0xFF | (((short) extraData) << 8)));
    }

}
