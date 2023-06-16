package pize.tests.voxelgame.clientserver.world;

import pize.tests.voxelgame.clientserver.chunk.Chunk;

public abstract class World{

    private final String name;
    
    public World(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    
    public abstract short getBlock(int x, int y, int z);
    
    public abstract void setBlock(int x, int y, int z, short block, boolean net);
    
    public abstract int getHeight(int x, int z);
    
    
    public abstract <M extends ChunkManager> M getChunkManager();
    
    public abstract  <C extends Chunk> C getChunk(int blockX, int blockZ);

}
