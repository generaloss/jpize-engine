package pize.tests.minecraftosp.client.level;

import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.main.level.Level;
import pize.tests.minecraftosp.client.chunk.ClientChunk;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class ClientLevel extends Level {
    
    private final Minecraft session;
    private final ClientChunkManager chunkManager;
    private final ClientLevelConfiguration configuration;
    
    public ClientLevel(Minecraft session, String levelName){
        this.session = session;
        this.chunkManager = new ClientChunkManager(this);
        this.configuration = new ClientLevelConfiguration();
        
        configuration.load(levelName);
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));
        
        return Blocks.VOID_AIR.getDefaultData();
    }
    
    @Override
    public boolean setBlock(int x, int y, int z, short block){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.setBlock(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), block);

        return false;
    }
    
    @Override
    public int getHeight(int x, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(HeightmapType.HIGHEST).getHeight(ChunkUtils.getLocalCoord(x), ChunkUtils.getLocalCoord(z));
        
        return 0;
    }
    
    
    @Override
    public byte getLight(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));
        
        return MAX_LIGHT_LEVEL;
    }
    
    @Override
    public void setLight(int x, int y, int z, int level){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), level);
    }
    
    
    @Override
    public ClientLevelConfiguration getConfiguration(){
        return configuration;
    }
    
    @Override
    public ClientChunkManager getChunkManager(){
        return chunkManager;
    }
    
    @Override
    public ClientChunk getBlockChunk(int blockX, int blockZ){
        return chunkManager.getChunk(ChunkUtils.getChunkPos(blockX), ChunkUtils.getChunkPos(blockZ));
    }
    
}
