package glit.tests.minecraft.client.world.chunk.storage;

import static glit.tests.minecraft.client.world.chunk.utils.IChunk.WIDTH_BITS;
import static glit.tests.minecraft.client.world.chunk.utils.IChunk.WIDTH_BITS2;

public abstract class ChunkData{

    static int getIndex(int x,int z){
        return x | z << WIDTH_BITS;
    }

    static int getIndex(int x,int y,int z){
        return x | y << WIDTH_BITS | z << WIDTH_BITS2;
    }

}
