package jpize.tests.minecraftosp.server.gen;

import jpize.tests.minecraftosp.server.chunk.ServerChunk;

public interface ChunkGenerator{

    void generate(ServerChunk chunk);

    void decorate(ServerChunk chunk, boolean other);

    String getID();
    
}
