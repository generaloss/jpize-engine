package megalul.projectvostok.chunk.storage;

import megalul.projectvostok.chunk.Chunk;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class ChunkBlockUtils{
    
    public static void updateNeighborChunksEdges(Chunk chunk, int x, int y, int z, short block){ //: NOT FAST (REBUILD NEIGHBOR FOR EACH FAST BLOCK SET)
        Chunk neighbor;
        if(x == 0){
            neighbor = getNeighborChunk(chunk, -1, 0);
            if(neighbor != null){
                neighbor.setBlock(SIZE, y, z, block);
                neighbor.rebuild();
            }
            if(z == 0){
                neighbor = getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, SIZE, block);
                    neighbor.rebuild();
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, -1, block);
                    neighbor.rebuild();
                }
            }
        }else if(x == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 1, 0);
            if(neighbor != null){
                neighbor.setBlock(-1, y, z, block);
                neighbor.rebuild();
            }
            if(z == 0){
                neighbor = getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, SIZE, block);
                    neighbor.rebuild();
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, -1, block);
                    neighbor.rebuild();
                }
            }
        }
        
        if(z == 0){
            neighbor = getNeighborChunk(chunk, 0, -1);
            if(neighbor != null){
                neighbor.setBlock(x, y, SIZE, block);
                neighbor.rebuild();
            }
            if(x == 0){
                neighbor = getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, SIZE, block);
                    neighbor.rebuild();
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, SIZE, block);
                    neighbor.rebuild();
                }
            }
        }else if(z == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 0, 1);
            if(neighbor != null){
                neighbor.setBlock(x, y, -1, block);
                neighbor.rebuild();
            }
            if(x == 0){
                neighbor = getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, -1, block);
                    neighbor.rebuild();
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, -1, block);
                    neighbor.rebuild();
                }
            }
        }
    }
    
}
