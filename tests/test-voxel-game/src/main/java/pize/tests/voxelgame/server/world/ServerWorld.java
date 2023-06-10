package pize.tests.voxelgame.server.world;

import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.player.OnlinePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ServerWorld{

    private final Server serverOF;
    
    private final String name;
    private final WorldConfiguration configuration;
    private final ServerChunkManager chunkManager;
    private final List<Entity> entities;
    private final List<OnlinePlayer> playersIn;

    public ServerWorld(Server serverOF, String name){
        this.serverOF = serverOF;
        
        this.name = name;
        configuration = new WorldConfiguration();
        chunkManager = new ServerChunkManager(this);
        entities = new ArrayList<>();
        playersIn = new ArrayList<>();
    }
    
    public Server getServerOf(){
        return serverOF;
    }
    
    public String getName(){
        return name;
    }


    public short getBlock(int x, int y, int z){
        final ServerChunk targetChunk = getChunkFromBlockPos(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));

        return Block.AIR.getState();
    }
    
    public void setBlock(int x, int y, int z, short block, boolean net){
        final ServerChunk targetChunk = getChunkFromBlockPos(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block, net);
    }
    
    
    public int getHeight(int x, int z){
        final ServerChunk targetChunk = getChunkFromBlockPos(x, z);
        if(targetChunk != null)
            return targetChunk.getStorage().getHeight(getLocalPos(x), getLocalPos(z));
        
        return 0;
    }
    
    
    public void updateEntities(){
        for(Entity entity: entities)
            entity.update();
    }
    
    public void putEntity(Entity entity){
        entities.add(entity);
    }
    

    public ServerChunkManager getChunkProvider(){
        return chunkManager;
    }
    
    public Collection<Entity> getEntities(){
        return entities;
    }
    
    
    public WorldConfiguration getConfiguration(){
        return configuration;
    }
    
    public List<OnlinePlayer> getPlayersIn(){
        return playersIn;
    }
    
    
    public ServerChunk getChunkFromBlockPos(int x, int z){
        return chunkManager.getChunk(getChunkPos(x), getChunkPos(z));
    }
    
}
