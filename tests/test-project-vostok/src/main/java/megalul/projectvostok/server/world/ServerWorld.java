package megalul.projectvostok.server.world;

import megalul.projectvostok.client.block.blocks.Block;
import megalul.projectvostok.clientserver.entity.Entity;
import megalul.projectvostok.clientserver.world.World;
import megalul.projectvostok.server.Server;
import megalul.projectvostok.server.chunk.ServerChunk;
import megalul.projectvostok.server.player.OnlinePlayer;
import megalul.projectvostok.server.world.light.WorldLight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static megalul.projectvostok.clientserver.chunk.ChunkUtils.getChunkPos;
import static megalul.projectvostok.clientserver.chunk.ChunkUtils.getLocalPos;

public class ServerWorld implements World{

    private final Server serverOF;
    
    private final WorldConfiguration configuration;
    private final ServerChunkManager chunkManager;
    private final List<Entity> entities;
    private final List<OnlinePlayer> playersIn;
    private final WorldLight light;

    public ServerWorld(Server serverOF){
        this.serverOF = serverOF;
        
        configuration = new WorldConfiguration();
        chunkManager = new ServerChunkManager(this);
        entities = new ArrayList<>();
        playersIn = new ArrayList<>();
        light = new WorldLight(this);
    }
    
    public Server getServerOf(){
        return serverOF;
    }


    @Override
    public short getBlock(int x, int y, int z){
        ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));

        return Block.AIR.getState();
    }
    
    @Override
    public byte getBlockID(int x, int y, int z){
        ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockID(getLocalPos(x), y, getLocalPos(z));
        
        return 0;
    }
    
    @Override
    public void setBlock(int x, int y, int z, short block){
        ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block);
    }
    
    
    public byte getSkyLight(int x, int y, int z){
        ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getSkyLight(getLocalPos(x), y, getLocalPos(z));
        
        return 0;
    }
    
    @Override
    public int getLight(int x, int y, int z){
        ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getLight(getLocalPos(x), y, getLocalPos(z));
        
        return 0;
    }
    
    public void setLight(int x, int y, int z, int level){ //: REMOVE
        ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null && level > targetChunk.getSkyLight(getLocalPos(x), y, getLocalPos(z)))
            targetChunk.setSkyLight(getLocalPos(x), y, getLocalPos(z), level);
    }
    
    public void updateSkyLight(int x, int z){
        ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null){
            light.updateChunkSkyLight(targetChunk, getLocalPos(x), getLocalPos(z));
            
            System.out.println(targetChunk.getPosition().x + " " + targetChunk.getPosition().z);
        }
    }
    
    
    public void updateEntities(){
        for(Entity entity: entities)
            entity.update();
    }
    
    public void putEntity(Entity entity){
        entities.add(entity);
    }


    public ServerChunk getChunk(int x, int z){
        return chunkManager.getChunk(getChunkPos(x), getChunkPos(z));
    }

    public ServerChunkManager getChunkProvider(){
        return chunkManager;
    }
    
    @Override
    public Collection<Entity> getEntities(){
        return entities;
    }
    
    
    public WorldConfiguration getConfiguration(){
        return configuration;
    }
    
    public WorldLight getLight(){
        return light;
    }
    
    public List<OnlinePlayer> getPlayersIn(){
        return playersIn;
    }

}
