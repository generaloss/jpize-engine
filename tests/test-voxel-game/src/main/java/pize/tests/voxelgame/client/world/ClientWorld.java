package pize.tests.voxelgame.client.world;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.clientserver.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ClientWorld extends World{
    
    private final Main sessionOF;
    private final ClientChunkManager chunkManager;
    private final List<Entity> entityList;
    
    public ClientWorld(Main sessionOF, String name){
        super(name);
        this.sessionOF = sessionOF;
        chunkManager = new ClientChunkManager(this);
        entityList = new ArrayList<>();
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


    public void tick(){
        for(Entity entity : entityList)
            entity.update();
    }


    public Collection<Entity> getEntities(){
        return entityList;
    }

    public Entity getEntity(String name){
        return entityList.get(0);
    }

    public void addEntity(Entity entity){
        entityList.add(entity);
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
