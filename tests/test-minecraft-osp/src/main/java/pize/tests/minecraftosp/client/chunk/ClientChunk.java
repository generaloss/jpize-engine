package pize.tests.minecraftosp.client.chunk;

import pize.math.vecmath.matrix.Matrix4f;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshStack;
import pize.tests.minecraftosp.client.control.camera.GameCamera;
import pize.tests.minecraftosp.client.level.ClientLevel;
import pize.tests.minecraftosp.main.block.BlockData;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;
import pize.tests.minecraftosp.main.chunk.LevelChunk;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.main.chunk.storage.Heightmap;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.main.level.ChunkManagerUtils;

public class ClientChunk extends LevelChunk {

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
        translationMatrix.toTranslated(
            position.globalX() - camera.getX(),
            0,
            position.globalZ() - camera.getZ()
        );
    }
    
    
    public boolean setBlock(int lx, int y, int lz, short blockData){
        if(!super.setBlock(lx, y, lz, blockData) || ChunkUtils.isOutOfBounds(lx, lz))
            return false;

        final Heightmap heightmapHighest = getHeightMap(HeightmapType.HIGHEST);
        heightmapHighest.update(lx, y, lz, BlockData.getID(blockData) != Blocks.AIR.getID());
        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);

        updateMaxY();
        
        return true;
    }
    
    public void setLight(int lx, int y, int lz, int level){
        super.setLight(lx, y, lz, level);
        rebuild(true);
    }
    
    
    public void rebuild(boolean important){
        getLevel().getChunkManager().rebuildChunk(this, important);
    }
    
    public ClientLevel getLevel(){
        return (ClientLevel) level;
    }
    
    public ClientChunk getNeighbor(int chunkX, int chunkZ){
        return getLevel().getChunkManager().getChunk(position.x + chunkX, position.z + chunkZ);
    }


    public int getMaxY(){
        return maxY;
    }

    public void updateMaxY(){
        maxY = 0;
        final Heightmap heightmapHighest = getHeightMap(HeightmapType.HIGHEST);
        for(short height: heightmapHighest.getValues())
            maxY = Math.max(maxY, height);
    }
    
}
