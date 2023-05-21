package pize.tests.minecraft.client.world.chunk.utils;

import pize.math.vecmath.tuple.Tuple2i;
import pize.tests.minecraft.client.world.block.BlockPos;

public class ChunkPos extends Tuple2i{

    public ChunkPos(){ }

    public ChunkPos(int x,int z){
        set(x,z);
    }

    public ChunkPos(Tuple2i tuple){
        set(tuple);
    }

    public ChunkPos(BlockPos blockPos){
        set(blockPos.x << IChunk.WIDTH_BITS, blockPos.z << IChunk.WIDTH_BITS);
    }

}
