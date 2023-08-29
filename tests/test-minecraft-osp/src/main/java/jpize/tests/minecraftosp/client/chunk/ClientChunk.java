package jpize.tests.minecraftosp.client.chunk;

import jpize.math.vecmath.matrix.Matrix4f;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.client.chunk.mesh.ChunkMeshStack;
import jpize.tests.minecraftosp.client.control.camera.GameCamera;
import jpize.tests.minecraftosp.client.level.ClientLevel;
import jpize.tests.minecraftosp.main.block.BlockData;
import jpize.tests.minecraftosp.main.chunk.ChunkUtils;
import jpize.tests.minecraftosp.main.chunk.LevelChunk;
import jpize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import jpize.tests.minecraftosp.main.chunk.storage.Heightmap;
import jpize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import jpize.tests.minecraftosp.main.level.ChunkManagerUtils;

public class ClientChunk extends LevelChunk{

    private final ChunkMeshStack meshStack;
    private final Matrix4f translationMatrix;
    private int maxY;

    public ClientChunk(ClientLevel level, ChunkPos position){
        super(level, position);
        meshStack = new ChunkMeshStack();
        translationMatrix = new Matrix4f();
    }


    public ChunkMeshStack getMeshStack(){
        return meshStack;
    }


    public Matrix4f getTranslationMatrix(){
        return translationMatrix;
    }

    public void updateTranslationMatrix(GameCamera camera){
        final float relativeChunkX = position.globalX() - camera.getX();
        final float relativeChunkZ = position.globalZ() - camera.getZ();
        translationMatrix.toTranslated(relativeChunkX, 0, relativeChunkZ);
    }


    @Override
    public boolean setBlockState(int lx, int y, int lz, short blockData){
        if(!super.setBlockState(lx, y, lz, blockData) || ChunkUtils.isOutOfBounds(lx, lz))
            return false;

        final boolean blockPlaced = BlockData.getID(blockData) != Blocks.AIR.getID();
        for(Heightmap heightmap: heightmaps.values())
            heightmap.update(lx, y, lz, blockPlaced);
        updateMaxY();

        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);

        return true;
    }

    @Override
    public boolean setBlock(int lx, int y, int lz, Block block){
        if(!super.setBlock(lx, y, lz, block) || ChunkUtils.isOutOfBounds(lx, lz))
            return false;

        final boolean blockPlaced = block != Blocks.AIR;
        for(Heightmap heightmap: heightmaps.values())
            heightmap.update(lx, y, lz, blockPlaced);
        updateMaxY();

        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);

        return true;
    }

    @Override
    public void setSkyLight(int lx, int y, int lz, int level){
        super.setSkyLight(lx, y, lz, level);
        rebuild(true);
    }

    @Override
    public void setBlockLight(int lx, int y, int lz, int level){
        super.setSkyLight(lx, y, lz, level);
        rebuild(true);
    }


    public void rebuild(boolean important){
        getLevel().getChunkManager().rebuildChunk(this, important);
    }

    @Override
    public ClientLevel getLevel(){
        return (ClientLevel) level;
    }

    @Override
    public ClientChunk getNeighborChunk(int signX, int signZ){
        return (ClientChunk) super.getNeighborChunk(signX, signZ);
    }


    public int getMaxY(){
        return maxY;
    }

    public void updateMaxY(){
        maxY = 0;
        final Heightmap heightmapSurface = getHeightMap(HeightmapType.SURFACE);
        for(short height: heightmapSurface.getValues())
            maxY = Math.max(maxY, height);
    }

}
