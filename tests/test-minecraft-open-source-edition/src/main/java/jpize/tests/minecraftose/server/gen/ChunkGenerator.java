package jpize.tests.minecraftose.server.gen;

import jpize.tests.minecraftose.server.chunk.ServerChunk;

public abstract class ChunkGenerator{

    public void generate(ServerChunk chunk){ }

    public void decorate(ServerChunk chunk){ }

    public abstract String getID();
    
}
