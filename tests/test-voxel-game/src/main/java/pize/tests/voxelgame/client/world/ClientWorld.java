package pize.tests.voxelgame.client.world;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.clientserver.world.World;

import java.util.Collection;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ClientWorld extends World{
    
    private final Main sessionOF;
    private final ClientChunkManager chunkManager;
    
    public ClientWorld(Main sessionOF, String name){
        super(name);
        this.sessionOF = sessionOF;
        chunkManager = new ClientChunkManager(this);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));
        
        return Block.AIR.getState();
    }
    
    @Override
    public void setBlock(int x, int y, int z, short block, boolean net){
        ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block, net);
    }
    
    @Override
    public int getHeight(int x, int z){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getStorage().getHeight(getLocalPos(x), getLocalPos(z));
        
        return 0;
    }
    
    
    public Collection<Entity> getEntities(){
        return null;
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
