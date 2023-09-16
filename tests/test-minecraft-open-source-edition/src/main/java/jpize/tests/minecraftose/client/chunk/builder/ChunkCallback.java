package jpize.tests.minecraftose.client.chunk.builder;

import jpize.tests.minecraftose.client.chunk.ClientChunk;

public interface ChunkCallback{

    void invoke(ClientChunk chunk, int lx, int lz);

}
