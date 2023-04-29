package pize.tests.minecraft.client.world.chunk.storage;

import pize.tests.minecraft.client.world.chunk.Chunk;

import static pize.tests.minecraft.client.world.chunk.utils.IChunk.*;

public class ChunkHeightMap extends ChunkData{

    private final Chunk chunkOf;
    private final byte[] heights;

    public ChunkHeightMap(Chunk chunkOf){
        this.chunkOf = chunkOf;

        heights = new byte[AREA];
    }


    public void update(int x,int y,int z,boolean destroyed){
        int index = getIndex(x,z);

        byte height = this.heights[index];
        if(y < height)
            return;

        if(y == height){
            if(destroyed)
                for(--height; height > -1; height--)
                    if( !chunkOf.getBlockState(x,height,z) .getType().isEmpty() ){
                        this.heights[index] = height;
                        return;
                    }
        }else // elif(y > height)
            this.heights[index] = (byte)y;
    }


    public byte get(int x,int z){
        return heights[getIndex(x,z)];
    }


    public byte[] array(){
        return heights;
    }

}
