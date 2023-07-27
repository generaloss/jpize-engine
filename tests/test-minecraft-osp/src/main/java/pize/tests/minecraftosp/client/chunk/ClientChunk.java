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
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.main.level.ChunkManagerUtils;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.isOutOfBounds;

public class ClientChunk extends LevelChunk {

    private final ChunkMeshStack meshStack;
    private final Matrix4f translationMatrix;
    
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
    
    
    public boolean setBlock(int lx, int y, int lz, short blockState){
        if(!super.setBlock(lx, y, lz, blockState) || ChunkUtils.isOutOfBounds(lx, lz))
            return false;
        
        getHeightMap(HeightmapType.HIGHEST).update(lx, y, lz, BlockData.getID(blockState) != Blocks.AIR.getID());
        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);
        
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
    
}
