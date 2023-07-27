package pize.tests.voxelgame.server.level;

import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.main.audio.Sound;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.main.level.Level;
import pize.tests.voxelgame.main.net.packet.CBPacketPlaySound;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.player.ServerPlayer;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.main.chunk.ChunkUtils.getLocalCoord;

public class ServerLevel extends Level{

    private final Server server;
    private final ServerChunkManager chunkManager;
    private final ServerLevelConfiguration configuration;
    private final LightEngine lightEngine;

    public ServerLevel(Server server){
        this.server = server;
        this.chunkManager = new ServerChunkManager(this);
        this.configuration = new ServerLevelConfiguration();
        this.lightEngine = new LightEngine(this);
    }
    
    public Server getServer(){
        return server;
    }
    
    
    @Override
    public short getBlock(int x, int y, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalCoord(x), y, getLocalCoord(z));

        return Blocks.VOID_AIR.getDefaultData();
    }
    
    @Override
    public boolean setBlock(int x, int y, int z, short block){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.setBlock(getLocalCoord(x), y, getLocalCoord(z), block);

        return false;
    }

    @Override
    public int getHeight(int x, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(HeightmapType.HIGHEST).getHeight(getLocalCoord(x), getLocalCoord(z));
        
        return 0;
    }
    
    
    @Override
    public byte getLight(int x, int y, int z){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getLight(getLocalCoord(x), y, getLocalCoord(z));
        
        return 15;
    }
    
    @Override
    public void setLight(int x, int y, int z, int level){
        final ServerChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setLight(getLocalCoord(x), y, getLocalCoord(z), level);
    }
    
    
    public Vec3f getSpawnPosition(){
        final Vec2f spawn = getConfiguration().getWorldSpawn();
        final int spawnY = getHeight(spawn.xf(), spawn.yf()) + 1;
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


    public LightEngine getLightEngine(){
        return lightEngine;
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
