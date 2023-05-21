package pize.tests.minecraft.client.world.chunk.utils;

import pize.tests.minecraft.client.world.chunk.Chunk;
import pize.tests.minecraft.client.world.chunk.ChunkSection;
import pize.tests.minecraft.client.world.block.BlockPos;
import pize.tests.minecraft.client.world.block.BlockState;

public class EmptyChunk extends Chunk{

    public EmptyChunk(){
        super(0,0);
    }


    @Override
    public BlockState getBlockState(int x,int y,int z){
        return BlockState.EMPTY;
    }

    @Override
    public void setBlockState(int x,int y,int z,BlockState state){ }

    @Override
    public BlockState getBlockState(BlockPos pos){
        return BlockState.EMPTY;
    }

    @Override
    public void setBlockState(BlockPos pos,BlockState state){ }


    @Override
    public ChunkSection[] getSections(){
        return null;
    }


    @Override
    public void setModified(boolean modified){ }

    @Override
    public boolean isModified(){
        return false;
    }

}
