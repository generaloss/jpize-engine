package glit.tests.minecraft.client.world.chunk;

import glit.tests.minecraft.client.world.chunk.storage.SectionBlockContainer;
import glit.tests.minecraft.client.world.block.BlockState;
import glit.tests.minecraft.client.world.chunk.utils.IChunk;

public class ChunkSection{

    private final SectionBlockContainer blocks;
    private final short y;
    private short notAirBlockNum;

    public ChunkSection(int y){
        blocks = new SectionBlockContainer();
        this.y = (short) y;
    }


    public BlockState getState(int x,int y,int z){
        return blocks.get(x,y,z);
    }

    public void setState(int x,int y,int z,BlockState state){
        BlockState swap = blocks.swap(x,y,z,state);

        if(state.getType().isEmpty() && !swap.getType().isEmpty())
            notAirBlockNum++;
        if(!state.getType().isEmpty() && swap.getType().isEmpty())
            notAirBlockNum--;
    }


    public SectionBlockContainer getBlocks(){
        return blocks;
    }

    public boolean isEmpty(){
        return notAirBlockNum == VOLUME;
    }

    public short getY(){
        return y;
    }


    public static final int HEIGHT = 16;
    public static final int VOLUME = IChunk.AREA * HEIGHT;
    public static final int HEIGHT_BITS = Integer.highestOneBit(HEIGHT);

}
