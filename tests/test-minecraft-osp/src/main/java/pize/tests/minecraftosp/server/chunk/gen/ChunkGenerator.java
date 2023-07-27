package pize.tests.minecraftosp.server.chunk.gen;

import pize.tests.minecraftosp.server.chunk.ServerChunk;

public interface ChunkGenerator{

    void generate(ServerChunk chunk);

    void generateDecorations(ServerChunk chunk);

    String getID();
    
}
