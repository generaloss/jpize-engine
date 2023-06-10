package pize.tests.voxelgame.client.world;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.clientserver.entity.Entity;

import java.util.Collection;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ClientWorld{
    
    private final Main sessionOF;
    private final ClientChunkManager chunkManager;
    private final String name;
    
    public ClientWorld(Main sessionOF, String name){
        this.sessionOF = sessionOF;
        this.name = name;
        chunkManager = new ClientChunkManager(this);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    public String getName(){
        return name;
    }
    
    
    public short getBlock(int x, int y, int z){
        ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));
        
        return Block.AIR.getState();
    }
    
    public void setBlock(int x, int y, int z, short block, boolean net){
        ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block, net);
    }
    
    
    public Collection<Entity> getEntities(){
        return null;
    }
    
    public ClientChunkManager getChunkManager(){
        return chunkManager;
    }
    
    
    public ClientChunk getChunk(int x, int z){
        return chunkManager.getChunk(getChunkPos(x), getChunkPos(z));
    }
    
}
