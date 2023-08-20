package pize.tests.minecraftosp.server.gen;

import pize.tests.minecraftosp.server.chunk.ServerChunk;

public interface ChunkGenerator{

    void generate(ServerChunk chunk);

    void decorate(ServerChunk chunk);

    String getID();
    
}
