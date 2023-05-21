package pize.tests.minecraft.client.world.chunk.storage;

import pize.tests.minecraft.client.world.block.BlockState;
import pize.tests.minecraft.client.world.chunk.ChunkSection;

public class SectionBlockContainer extends ChunkData{

    private final BlockState[] blocks;

    public SectionBlockContainer(){
        blocks = new BlockState[ChunkSection.VOLUME];
    }


    public BlockState get(int x,int y,int z){
        return blocks[getIndex(x,y,z)];
    }

    public void set(int x,int y,int z,BlockState state){
        blocks[getIndex(x,y,z)] = state;
    }

    public BlockState swap(int x,int y,int z,BlockState state){
        BlockState swap = blocks[getIndex(x,y,z)];
        blocks[getIndex(x,y,z)] = state;
        return swap;
    }


    public BlockState[] array(){
        return blocks;
    }

}
