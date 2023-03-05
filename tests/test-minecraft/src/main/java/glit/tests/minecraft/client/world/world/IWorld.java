package glit.tests.minecraft.client.world.world;

import glit.tests.minecraft.client.world.chunk.utils.ChunkPos;

public abstract class IWorld{

    private final WorldInfo info;

    public IWorld(){
        info = new WorldInfo(null);
    }


    public WorldInfo getWorldInfo(){
        return info;
    }

    boolean chunkExists(ChunkPos chunkPos){
        return getChunkProvider().chunkExists(chunkPos);
    }

    abstract public IChunkProvider getChunkProvider();


    public static int MAX_SIZE = 29999984;

}
