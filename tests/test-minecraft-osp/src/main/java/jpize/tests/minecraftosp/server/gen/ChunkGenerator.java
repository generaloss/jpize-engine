package jpize.tests.minecraftosp.server.gen;

import jpize.tests.minecraftosp.server.chunk.ServerChunk;

public abstract class ChunkGenerator{

    public void generate(ServerChunk chunk){ }

    public void decorate(ServerChunk chunk){ }

    public abstract String getID();
    
}
