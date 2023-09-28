package jpize.tests.mcose.client.chunk.builder;

import jpize.tests.mcose.client.chunk.ClientChunk;

public interface ChunkCallback{

    void invoke(ClientChunk chunk, int lx, int lz);

}
