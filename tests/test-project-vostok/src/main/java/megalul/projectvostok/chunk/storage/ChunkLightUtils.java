package megalul.projectvostok.chunk.storage;

import megalul.projectvostok.chunk.Chunk;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class ChunkLightUtils{
    
    public static void updateSkyLightEdgesOfNeighborChunks(Chunk chunk, int x, int y, int z, int level){ //: NOT FAST (REBUILD NEIGHBOR FOR EACH FAST BLOCK SET)
        Chunk neighbor;
        if(x == 0){
            neighbor = getNeighborChunk(chunk, -1, 0);
            if(neighbor != null){
                neighbor.getStorage().setSkyLight(SIZE, y, z, level);
            }
            if(z == 0){
                neighbor = getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(SIZE, y, SIZE, level);
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(SIZE, y, -1, level);
                }
            }
        }else if(x == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 1, 0);
            if(neighbor != null){
                neighbor.getStorage().setSkyLight(-1, y, z, level);
            }
            if(z == 0){
                neighbor = getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(-1, y, SIZE, level);
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(-1, y, -1, level);
                }
            }
        }
        
        if(z == 0){
            neighbor = getNeighborChunk(chunk, 0, -1);
            if(neighbor != null){
                neighbor.getStorage().setSkyLight(x, y, SIZE, level);
            }
            if(x == 0){
                neighbor = getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(SIZE, y, SIZE, level);
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(-1, y, SIZE, level);
                }
            }
        }else if(z == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 0, 1);
            if(neighbor != null){
                neighbor.getStorage().setSkyLight(x, y, -1, level);
            }
            if(x == 0){
                neighbor = getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(SIZE, y, -1, level);
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setSkyLight(-1, y, -1, level);
                }
            }
        }
    }
    
    public static void updateBlockLightEdgesOfNeighborChunks(Chunk chunk, int x, int y, int z, int level){ //: NOT FAST (REBUILD NEIGHBOR FOR EACH FAST BLOCK SET)
        Chunk neighbor;
        if(x == 0){
            neighbor = getNeighborChunk(chunk, -1, 0);
            if(neighbor != null){
                neighbor.getStorage().setBlockLight(SIZE, y, z, level);
            }
            if(z == 0){
                neighbor = getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(SIZE, y, SIZE, level);
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(SIZE, y, -1, level);
                }
            }
        }else if(x == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 1, 0);
            if(neighbor != null){
                neighbor.getStorage().setBlockLight(-1, y, z, level);
            }
            if(z == 0){
                neighbor = getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(-1, y, SIZE, level);
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(-1, y, -1, level);
                }
            }
        }
        
        if(z == 0){
            neighbor = getNeighborChunk(chunk, 0, -1);
            if(neighbor != null){
                neighbor.getStorage().setBlockLight(x, y, SIZE, level);
            }
            if(x == 0){
                neighbor = getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(SIZE, y, SIZE, level);
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(-1, y, SIZE, level);
                }
            }
        }else if(z == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 0, 1);
            if(neighbor != null){
                neighbor.getStorage().setBlockLight(x, y, -1, level);
            }
            if(x == 0){
                neighbor = getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(SIZE, y, -1, level);
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.getStorage().setBlockLight(-1, y, -1, level);
                }
            }
        }
    }
    
}
