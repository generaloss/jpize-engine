package jpize.tests.minecraftosp.client.level;

import jpize.math.Maths;
import jpize.tests.minecraftosp.client.ClientGame;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.client.chunk.ClientChunk;
import jpize.tests.minecraftosp.main.biome.Biome;
import jpize.tests.minecraftosp.main.chunk.ChunkUtils;
import jpize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import jpize.tests.minecraftosp.main.level.Level;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class ClientLevel extends Level{

    private final ClientGame game;
    private final ClientChunkManager chunkManager;
    private final ClientLevelConfiguration configuration;

    public ClientLevel(ClientGame game, String levelName){
        this.game = game;
        this.chunkManager = new ClientChunkManager(this);
        this.configuration = new ClientLevelConfiguration();

        configuration.load(levelName);
    }

    public ClientGame getGame(){
        return game;
    }


    @Override
    public short getBlockState(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockState(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return Blocks.VOID_AIR.getDefaultData();
    }

    @Override
    public Block getBlock(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return Blocks.VOID_AIR;
    }

    @Override
    public boolean setBlockState(int x, int y, int z, short state){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.setBlockData(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), state);

        return false;
    }

    @Override
    public boolean setBlock(int x, int y, int z, Block block){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.setBlock(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), block);

        return false;
    }

    @Override
    public int getHeight(HeightmapType heightmapType, int x, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(heightmapType).getHeight(ChunkUtils.getLocalCoord(x), ChunkUtils.getLocalCoord(z));

        return 0;
    }


    @Override
    public int getSkyLight(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getSkyLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return MAX_LIGHT_LEVEL;
    }

    @Override
    public void setSkyLight(int x, int y, int z, int level){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setSkyLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), level);
    }

    @Override
    public int getBlockLight(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return MAX_LIGHT_LEVEL;
    }

    @Override
    public void setBlockLight(int x, int y, int z, int level){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlockLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), level);
    }

    public int getLight(int x, int y, int z){
        final int skyLight = getSkyLight(x, y, z);
        final int blockLight = getBlockLight(x, y, z);
        final float skyBrightness = game.getSession().getRenderer().getWorldRenderer().getSkyRenderer().getSkyBrightness();

        return Math.max(Maths.round(skyLight * skyBrightness), blockLight);
    }


    public Biome getBiome(int x, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBiomes().getBiome(ChunkUtils.getLocalCoord(x), ChunkUtils.getLocalCoord(z));

        return null;
    }


    @Override
    public ClientLevelConfiguration getConfiguration(){
        return configuration;
    }

    @Override
    public ClientChunkManager getChunkManager(){
        return chunkManager;
    }

    @Override
    public ClientChunk getBlockChunk(int blockX, int blockZ){
        return chunkManager.getChunk(ChunkUtils.getChunkPos(blockX), ChunkUtils.getChunkPos(blockZ));
    }

}
