package pize.tests.voxelgame.server.chunk.gen;

import pize.tests.voxelgame.server.chunk.ServerChunk;

public interface ChunkGenerator{

    void generate(ServerChunk chunk);

    void generateDecorations(ServerChunk chunk);

    String getID();
    
}
