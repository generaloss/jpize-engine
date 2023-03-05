package glit.tests.minecraft.server.world.chunk;

import glit.tests.minecraft.client.world.block.BlockState;
import glit.tests.minecraft.client.world.chunk.ChunkSection;
import glit.tests.minecraft.client.world.chunk.utils.IChunk;

public class ChunkLoader{

    public boolean load(IChunk chunk){
        for(int section = 0; section < IChunk.SECTIONS; section++)
            for(int i = 0; i < ChunkSection.VOLUME; i++)
                chunk.getSections()[section].getBlocks().array()[i] = BlockState.EMPTY;

        return true;
    }

}
