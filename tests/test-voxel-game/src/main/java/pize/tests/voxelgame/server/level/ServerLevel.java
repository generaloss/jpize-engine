package pize.tests.voxelgame.server.level;

import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.main.level.Level;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.chunk.ServerChunk;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.main.chunk.ChunkUtils.getLocalCoord;

public class ServerLevel extends Level{

    private final Server server;
    private final ServerChunkManager chunkManager;
    private final ServerLevelConfiguration configuration;

    public ServerLevel(Server server){
        this.server = server;
        this.chunkManager = new ServerChunkManager(this);
        this.configuration = new ServerLevelConfiguration();
    }
    
    public Server getServer(){
        return server;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalCoord(x), y, getLocalCoord(z));

        return Blocks.VOID_AIR.getDefaultState();
    }
    
    @Override
    public void setBlock(int x, int y, int z, short block){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalCoord(x), y, getLocalCoord(z), block);
    }
    
    @Override
    public int getHeight(int x, int z){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(HeightmapType.SURFACE).getHeight(getLocalCoord(x), getLocalCoord(z));
        
        return 0;
    }
    
    
    @Override
    public byte getLight(int x, int y, int z){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getLight(getLocalCoord(x), y, getLocalCoord(z));
        
        return 15;
    }
    
    @Override
    public void setLight(int x, int y, int z, int level){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setLight(getLocalCoord(x), y, getLocalCoord(z), level);
    }
    
    
    public Vec3f getSpawnPosition(){
        final Vec2f spawn = getConfiguration().getWorldSpawn();
        final int spawnY = getHeight(spawn.xf(), spawn.yf()) + 1;
        return new Vec3f(spawn.x, spawnY, spawn.y);
    }
    
    
    @Override
    public ServerLevelConfiguration getConfiguration(){
        return configuration;
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
