package jpize.tests.mcose.main.chunk.neighbors;

import jpize.tests.mcose.main.chunk.LevelChunk;
import jpize.tests.mcose.main.chunk.storage.ChunkPos;
import jpize.tests.mcose.main.level.ChunkManager;

/**  ◯───────→ X
 *   │ [0][1][2]
 *   │ [3] 4 [5]
 *   ↓ [6][7][8]
 *   Z
 */
public class LevelChunkNeighbors{

    private final ChunkManager chunkManager;
    private final ChunkPos[] neighbors;
    private final int distance, size;

    public LevelChunkNeighbors(LevelChunk chunk, int distance){
        this.chunkManager = chunk.getLevel().getChunkManager();

        this.distance = distance;
        this.size = distance * 2 + 1;

        this.neighbors = new ChunkPos[size * size];
        for(int i = 0; i < neighbors.length; i++){
            final int x = i % size;
            final int z = (i - x) / size;

            neighbors[i] = chunk.getPosition().getNeighbor(x - distance, z - distance);
        }
    }

    public ChunkPos[] array(){
        return neighbors;
    }

    public int index(int neighborX, int neighborZ){
        return (neighborZ + distance) * size + (neighborX + distance);
    }

    public ChunkPos getNeighborPos(int neighborX, int neighborZ){
        return neighbors[index(neighborX, neighborZ)];
    }

    public LevelChunk getNeighborChunk(int neighborX, int neighborZ){
        return chunkManager.getChunk(getNeighborPos(neighborX, neighborZ));
    }

}
