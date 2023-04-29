package pize.tests.minecraft.server.world.gen;

import pize.tests.minecraft.client.world.chunk.utils.IChunk;

public interface ChunkGenerator{

    void generate(IChunk chunk);

}
