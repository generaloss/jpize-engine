package jpize.tests.minecraftosp.client.chunk.builder;

import jpize.tests.minecraftosp.client.chunk.ClientChunk;

public interface ChunkCallback{

    void invoke(ClientChunk chunk, int lx, int lz);

}
