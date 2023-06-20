package pize.tests.voxelgame.client.level;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.clientserver.chunk.storage.HeightmapType;
import pize.tests.voxelgame.clientserver.level.Level;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ClientLevel extends Level{
    
    private final Main session;
    private final ClientChunkManager chunkManager;
    private final ClientLevelConfiguration configuration;
    
    public ClientLevel(Main session, String levelName){
        this.session = session;
        this.chunkManager = new ClientChunkManager(this);
        
        configuration = new ClientLevelConfiguration();
        configuration.load(levelName);
    }
    
    public Main getSession(){
        return session;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));
        
        return Block.AIR.getDefaultState();
    }
    
    @Override
    public void setBlock(int x, int y, int z, short block){
        ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block);
    }
    
    @Override
    public int getHeight(int x, int z){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(HeightmapType.SURFACE).getHeight(getLocalPos(x), getLocalPos(z));
        
        return 0;
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
