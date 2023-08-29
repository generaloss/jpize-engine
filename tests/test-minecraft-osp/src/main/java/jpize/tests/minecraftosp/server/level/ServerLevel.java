package jpize.tests.minecraftosp.server.level;

import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.main.audio.Sound;
import jpize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import jpize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import jpize.tests.minecraftosp.main.level.Level;
import jpize.tests.minecraftosp.main.net.packet.CBPacketPlaySound;
import jpize.tests.minecraftosp.server.Server;
import jpize.tests.minecraftosp.server.chunk.ServerChunk;
import jpize.tests.minecraftosp.server.level.chunk.ServerChunkManager;
import jpize.tests.minecraftosp.server.level.light.LevelBlockLight;
import jpize.tests.minecraftosp.server.level.light.LevelSkyLight;
import jpize.tests.minecraftosp.server.player.ServerPlayer;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class ServerLevel extends Level{

    private final Server server;
    private final ServerChunkManager chunkManager;
    private final ServerLevelConfiguration configuration;
    private final LevelSkyLight skyLight;
    private final LevelBlockLight blockLight;

    public ServerLevel(Server server){
        this.server = server;
        this.chunkManager = new ServerChunkManager(this);
        this.configuration = new ServerLevelConfiguration();
        this.skyLight = new LevelSkyLight(this);
        this.blockLight = new LevelBlockLight(this);
    }
    
    public Server getServer(){
        return server;
    }
    
    
    @Override
    public short getBlockState(int x, int y, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockState(getLocalCoord(x), y, getLocalCoord(z));

        return Blocks.VOID_AIR.getDefaultData();
    }
    
    @Override
    public boolean setBlockState(int x, int y, int z, short blockData){
        final ChunkPos chunkPos = new ChunkPos(getChunkPos(x), getChunkPos(z));
        ServerChunk targetChunk = chunkManager.getChunk(chunkPos);

        final int lx = getLocalCoord(x);
        final int lz = getLocalCoord(z);

        if(targetChunk != null)
            return targetChunk.setBlockState(lx, y, lz, blockData);

        targetChunk = chunkManager.getGeneratingChunk(chunkPos);
        if(targetChunk != null)
            return targetChunk.setBlockState(lx, y, lz, blockData);

        return false;
    }


    @Override
    public Block getBlock(int x, int y, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalCoord(x), y, getLocalCoord(z));

        return Blocks.VOID_AIR;
    }

    @Override
    public boolean setBlock(int x, int y, int z, Block block){
        final ChunkPos chunkPos = new ChunkPos(getChunkPos(x), getChunkPos(z));
        ServerChunk targetChunk = chunkManager.getChunk(chunkPos);

        final int lx = getLocalCoord(x);
        final int lz = getLocalCoord(z);

        if(targetChunk != null)
            return targetChunk.setBlock(lx, y, lz, block);

        targetChunk = chunkManager.getGeneratingChunk(chunkPos);
        if(targetChunk != null)
            return targetChunk.setBlock(lx, y, lz, block);

        return false;
    }

    public void setBlockDec(ServerChunk from, boolean others, int x, int y, int z, Block block){
        final ChunkPos chunkPos = new ChunkPos(getChunkPos(x), getChunkPos(z));
        ServerChunk targetChunk = chunkManager.getChunk(chunkPos);

        final int lx = getLocalCoord(x);
        final int lz = getLocalCoord(z);

        if(targetChunk != null)
            targetChunk.setBlockDec(from, others, lx, y, lz, block);

        targetChunk = chunkManager.getGeneratingChunk(chunkPos);
        if(targetChunk != null)
            targetChunk.setBlockDec(from, others, lx, y, lz, block);
    }


    @Override
    public int getHeight(HeightmapType heightmapType, int x, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(heightmapType).getHeight(getLocalCoord(x), getLocalCoord(z));
        
        return 0;
    }
    
    
    @Override
    public int getSkyLight(int x, int y, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getSkyLight(getLocalCoord(x), y, getLocalCoord(z));
        
        return MAX_LIGHT_LEVEL;
    }
    
    @Override
    public void setSkyLight(int x, int y, int z, int level){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setSkyLight(getLocalCoord(x), y, getLocalCoord(z), level);
    }

    @Override
    public int getBlockLight(int x, int y, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockLight(getLocalCoord(x), y, getLocalCoord(z));

        return 0;
    }

    @Override
    public void setBlockLight(int x, int y, int z, int level){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlockLight(getLocalCoord(x), y, getLocalCoord(z), level);
    }
    
    
    public Vec3f getSpawnPosition(){
        final Vec2f spawn = getConfiguration().getWorldSpawn();
        final int spawnY = getHeight(HeightmapType.SURFACE, spawn.xf(), spawn.yf()) + 1;
        return new Vec3f(spawn.x, spawnY, spawn.y);
    }


    public void playSound(Sound sound, float volume, float pitch, float x, float y, float z){
        server.getPlayerList().broadcastPacketLevel(
            this, new CBPacketPlaySound(sound, volume, pitch, x, y, z)
        );
    }

    public void playSound(Sound sound, float volume, float pitch, Vec3f position){
        server.getPlayerList().broadcastPacketLevel(
            this, new CBPacketPlaySound(sound, volume, pitch, position)
        );
    }

    public void playSoundExcept(ServerPlayer except, Sound sound, float volume, float pitch, float x, float y, float z){
        server.getPlayerList().broadcastPacketLevelExcept(
            this,
            new CBPacketPlaySound(sound, volume, pitch, x, y, z),
            except
        );
    }


    public LevelSkyLight getSkyLight(){
        return skyLight;
    }

    public LevelBlockLight getBlockLight(){
        return blockLight;
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
    public ServerChunk getBlockChunk(int blockX, int blockZ){
        return chunkManager.getChunk(getChunkPos(blockX), getChunkPos(blockZ));
    }
    
}
