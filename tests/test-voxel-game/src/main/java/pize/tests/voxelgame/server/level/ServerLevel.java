package pize.tests.voxelgame.server.level;

import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.clientserver.level.Level;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.chunk.gen.DefaultGenerator;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getLocalPos;

public class ServerLevel extends Level{

    private final Server serverOF;
    
    private final LevelConfiguration configuration;
    private final ServerChunkManager chunkManager;

    public ServerLevel(Server serverOF, String name){
        super(name);
        this.serverOF = serverOF;
        
        configuration = new LevelConfiguration();
        configuration.load(name, DefaultGenerator.getInstance());
        chunkManager = new ServerChunkManager(this);
    }
    
    public Server getServer(){
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
    public void setBlock(int x, int y, int z, short block){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block);
    }
    
    @Override
    public int getHeight(int x, int z){
        final ServerChunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getStorage().getHeight(getLocalPos(x), getLocalPos(z));
        
        return 0;
    }

    
    public Vec3f getSpawnPosition(){
        final Vec2f spawn = configuration.getWorldSpawn();
        final int spawnY = getHeight(spawn.xf(), spawn.yf()) + 1;
        return new Vec3f(spawn.x, spawnY, spawn.y);
    }
    
    
    public LevelConfiguration getConfiguration(){
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
