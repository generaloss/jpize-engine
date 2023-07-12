package pize.tests.voxelgame.client.level;

import pize.tests.voxelgame.VoxelGame;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.main.level.Level;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.client.chunk.ClientChunk;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.main.chunk.ChunkUtils.getLocalCoord;

public class ClientLevel extends Level{
    
    private final VoxelGame session;
    private final ClientChunkManager chunkManager;
    private final ClientLevelConfiguration configuration;
    
    public ClientLevel(VoxelGame session, String levelName){
        this.session = session;
        this.chunkManager = new ClientChunkManager(this);
        this.configuration = new ClientLevelConfiguration();
        
        configuration.load(levelName);
    }
    
    public VoxelGame getSession(){
        return session;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalCoord(x), y, getLocalCoord(z));
        
        return Blocks.VOID_AIR.getDefaultState();
    }
    
    @Override
    public void setBlock(int x, int y, int z, short block){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalCoord(x), y, getLocalCoord(z), block);
    }
    
    @Override
    public int getHeight(int x, int z){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(HeightmapType.SURFACE).getHeight(getLocalCoord(x), getLocalCoord(z));
        
        return 0;
    }
    
    
    @Override
    public byte getLight(int x, int y, int z){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getLight(getLocalCoord(x), y, getLocalCoord(z));
        
        return 15;
    }
    
    @Override
    public void setLight(int x, int y, int z, int level){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setLight(getLocalCoord(x), y, getLocalCoord(z), level);
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
    public ClientChunk getChunk(int blockX, int blockZ){
        return chunkManager.getChunk(getChunkPos(blockX), getChunkPos(blockZ));
    }
    
}
