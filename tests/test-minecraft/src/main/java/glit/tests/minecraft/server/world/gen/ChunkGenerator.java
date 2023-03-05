package glit.tests.minecraft.server.world.gen;

import glit.tests.minecraft.client.world.chunk.utils.IChunk;

public interface ChunkGenerator{

    void generate(IChunk chunk);

}
