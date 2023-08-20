package pize.tests.minecraftosp.client.chunk.mesh.builder;

import pize.tests.minecraftosp.client.chunk.ClientChunk;

public interface ChunkCallback{

    void invoke(ClientChunk chunk, int lx, int lz);

}
