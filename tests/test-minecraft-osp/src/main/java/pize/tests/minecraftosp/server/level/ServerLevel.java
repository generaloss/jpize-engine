package pize.tests.minecraftosp.server.level;

import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.server.level.chunk.ServerChunkManager;
import pize.tests.minecraftosp.server.player.ServerPlayer;
import pize.tests.minecraftosp.main.audio.Sound;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.main.level.Level;
import pize.tests.minecraftosp.main.net.packet.CBPacketPlaySound;
import pize.tests.minecraftosp.server.Server;
import pize.tests.minecraftosp.server.chunk.ServerChunk;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class ServerLevel extends Level{

    private final Server server;
    private final ServerChunkManager chunkManager;
    private final ServerLevelConfiguration configuration;
    private final LevelLight levelLight;

    public ServerLevel(Server server){
        this.server = server;
        this.chunkManager = new ServerChunkManager(this);
        this.configuration = new ServerLevelConfiguration();
        this.levelLight = new LevelLight(this);
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
    public boolean setBlock(int x, int y, int z, short blockData){
        final ChunkPos chunkPos = new ChunkPos(getChunkPos(x), getChunkPos(z));
        final ServerChunk targetChunk = chunkManager.getChunk(chunkPos);

        final int lx = getLocalCoord(x);
        final int lz = getLocalCoord(z);

        if(targetChunk != null)
            return targetChunk.setBlock(lx, y, lz, blockData);

        chunkManager.getBlockPool().setBlock(chunkPos, lx, y, lz, blockData);
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
        
        return MAX_LIGHT_LEVEL;
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


    public LevelLight getLight(){
        return levelLight;
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
