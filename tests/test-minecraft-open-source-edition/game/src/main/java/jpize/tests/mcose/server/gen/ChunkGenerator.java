package jpize.tests.mcose.server.gen;

import jpize.tests.mcose.server.chunk.ServerChunk;

public abstract class ChunkGenerator{

    public void generate(ServerChunk chunk){ }

    public void decorate(ServerChunk chunk){ }

    public abstract String getID();
    
}
