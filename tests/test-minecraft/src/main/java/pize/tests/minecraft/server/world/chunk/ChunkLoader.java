package pize.tests.minecraft.server.world.chunk;

import pize.tests.minecraft.client.world.block.BlockState;
import pize.tests.minecraft.client.world.chunk.ChunkSection;
import pize.tests.minecraft.client.world.chunk.utils.IChunk;

public class ChunkLoader{

    public boolean load(IChunk chunk){
        for(int section = 0; section < IChunk.SECTIONS; section++)
            for(int i = 0; i < ChunkSection.VOLUME; i++)
                chunk.getSections()[section].getBlocks().array()[i] = BlockState.EMPTY;

        return true;
    }

}
