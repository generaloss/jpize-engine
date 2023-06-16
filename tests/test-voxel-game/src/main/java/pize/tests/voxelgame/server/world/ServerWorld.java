package pize.tests.voxelgame.server.world;

import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.clientserver.world.World;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.player.OnlinePlayer;

import java.util.ArrayList;
import java.util.List;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ServerWorld extends World{

    private final Server serverOF;
    
    private final WorldConfiguration configuration;
    private final ServerChunkManager chunkManager;
    private final List<Entity> entities;
    private final List<OnlinePlayer> playersIn;

    public ServerWorld(Server serverOF, String name){
        super(name);
        this.serverOF = serverOF;
        
        configuration = new WorldConfiguration();
        chunkManager = new ServerChunkManager(this);
        entities = new ArrayList<>();
        playersIn = new ArrayList<>();
    }
    
    public Server getServerOf(){
        return serverOF;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));

        return Block.AIR.getState();
    }
    
    @Override
    public void setBlock(int x, int y, int z, short block, boolean net){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block, net);
    }
    
    @Override
    public int getHeight(int x, int z){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getStorage().getHeight(getLocalPos(x), getLocalPos(z));
        
        return 0;
    }
    
    
    public WorldConfiguration getConfiguration(){
        return configuration;
    }
    
    public List<OnlinePlayer> getPlayersIn(){
        return playersIn;
    }
    
    @Override
    public ServerChunkManager getChunkManager(){
        return chunkManager;
    }
    
    @Override
    public ServerChunk getChunk(int blockX, int blockZ){
        return chunkManager.getChunk(getChunkPos(blockX), getChunkPos(blockZ));
    }
    
}
