package jpize.tests.mcose.main.chunk.neighbors;


import jpize.tests.mcose.main.chunk.LevelChunk;
import jpize.tests.mcose.main.chunk.storage.ChunkPos;

/**          Quad
 *   [ 0][ 1][ 2][ 3][ 4]
 *   [15]      :     [ 5]
 *   [14].. [Chunk]..[ 6]--
 *   [13]      :     [ 7] | distance = 2
 *   [12][11][10][ 9][ 8]--
 *     |__ size = 5 __|
 */
public class LevelChunkRing{

    private final ChunkPos[] ring;
    private final int size;

    public LevelChunkRing(LevelChunk chunk, int distance){
        this.size = distance * 2 + 1;
        this.ring = new ChunkPos[(size - 1) * 4];

        for(int i = 0; i < ring.length; i++){
            final int x = i - distance;
            final int z = 0;

            ring[i] = chunk.getPosition().getNeighbor(x, z);
        }
    }

    public ChunkPos[] array(){
        return ring;
    }



}
