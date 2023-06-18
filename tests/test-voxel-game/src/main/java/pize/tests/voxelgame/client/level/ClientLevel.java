package pize.tests.voxelgame.client.level;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.clientserver.level.Level;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ClientLevel extends Level{
    
    private final Main sessionOF;
    private final ClientChunkManager chunkManager;
    
    
    public ClientLevel(Main sessionOF, String name){
        super(name);
        this.sessionOF = sessionOF;
        chunkManager = new ClientChunkManager(this);
    }
    
    public Main getSession(){
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
    public void setBlock(int x, int y, int z, short block){
        ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block);
    }
    
    @Override
    public int getHeight(int x, int z){
        final ClientChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getStorage().getHeight(getLocalPos(x), getLocalPos(z));
        
        return 0;
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
