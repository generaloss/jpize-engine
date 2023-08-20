package pize.tests.minecraftosp.client.block;

import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.block.BlockData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Block{
    
    private final byte ID;
    private final short defaultState;
    protected Map<Byte, BlockProps> states;

    public Block(int ID){
        this.ID = (byte) ID;
        this.defaultState = BlockData.getData(ID);
        this.states = new HashMap<>(1);
    }

    public final byte getID(){
        return ID;
    }

    public short getDefaultData(){
        return defaultState;
    }

    public short getData(int state){
        return BlockData.getData(ID, state);
    }


    /** Возвращает True если это воздух */
    public final boolean isEmpty(){
        return ID == Blocks.AIR.getID();
    }


    public Collection<BlockProps> getStates(){
        return states.values();
    }

    public BlockProps getState(byte state){
        return states.get(state);
    }

    public BlockProps getState(int index){
        return states.get((byte) index);
    }

    protected BlockProps newState(){
        final byte state = (byte) states.size();
        final BlockProps blockProps = new BlockProps(this, state);

        states.put(state, blockProps);
        return blockProps;
    }


    public abstract void load(GameResources resources);


    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;

        if(object == null || getClass() != object.getClass())
            return false;

        final Block block = (Block) object;
        return ID == block.ID;
    }

    @Override
    public int hashCode(){
        return ID;
    }

}
