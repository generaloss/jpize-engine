package pize.tests.voxelgame.clientserver.chunk.storage;

import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.clientserver.chunk.ChunkUtils;
import pize.tests.voxelgame.server.chunk.ServerChunk;

public class ChunkBlockUtils{
    
    public static void updateNeighborChunksEdges(ClientChunk chunk, int x, int y, int z, short block){ //: NOT FAST (REBUILD NEIGHBOR FOR EACH FAST BLOCK SET)
        ClientChunk neighbor;
        if(x == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 0);
            if(neighbor != null){
                neighbor.setBlock(ChunkUtils.SIZE, y, z, block);
                neighbor.rebuild(true);
            }
            if(z == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.setBlock(ChunkUtils.SIZE, y, ChunkUtils.SIZE, block);
                    neighbor.rebuild(true);
                }
            }else if(z == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.setBlock(ChunkUtils.SIZE, y, -1, block);
                    neighbor.rebuild(true);
                }
            }
        }else if(x == ChunkUtils.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 0);
            if(neighbor != null){
                neighbor.setBlock(-1, y, z, block);
                neighbor.rebuild(true);
            }
            if(z == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, ChunkUtils.SIZE, block);
                    neighbor.rebuild(true);
                }
            }else if(z == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, -1, block);
                    neighbor.rebuild(true);
                }
            }
        }
        
        if(z == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, -1);
            if(neighbor != null){
                neighbor.setBlock(x, y, ChunkUtils.SIZE, block);
                neighbor.rebuild(true);
            }
            if(x == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, -1);
                if(neighbor != null){
                    neighbor.setBlock(ChunkUtils.SIZE, y, ChunkUtils.SIZE, block);
                    neighbor.rebuild(true);
                }
            }else if(x == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, -1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, ChunkUtils.SIZE, block);
                    neighbor.rebuild(true);
                }
            }
        }else if(z == ChunkUtils.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, 1);
            if(neighbor != null){
                neighbor.setBlock(x, y, -1, block);
                neighbor.rebuild(true);
            }
            if(x == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 1);
                if(neighbor != null){
                    neighbor.setBlock(ChunkUtils.SIZE, y, -1, block);
                    neighbor.rebuild(true);
                }
            }else if(x == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, -1, block);
                    neighbor.rebuild(true);
                }
            }
        }
    }
    
    
    public static void updateNeighborChunksEdges(ServerChunk chunk, int x, int y, int z, short block){ //: NOT FAST (REBUILD NEIGHBOR FOR EACH FAST BLOCK SET)
        ServerChunk neighbor;
        if(x == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 0);
            if(neighbor != null)
                neighbor.setBlock(ChunkUtils.SIZE, y, z, block);
            if(z == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, -1);
                if(neighbor != null)
                    neighbor.setBlock(ChunkUtils.SIZE, y, ChunkUtils.SIZE, block);
            }else if(z == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 1);
                if(neighbor != null)
                    neighbor.setBlock(ChunkUtils.SIZE, y, -1, block);
            }
        }else if(x == ChunkUtils.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 0);
            if(neighbor != null)
                neighbor.setBlock(-1, y, z, block);
            if(z == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, -1);
                if(neighbor != null)
                    neighbor.setBlock(-1, y, ChunkUtils.SIZE, block);
            }else if(z == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 1);
                if(neighbor != null)
                    neighbor.setBlock(-1, y, -1, block);
            }
        }
        
        if(z == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, -1);
            if(neighbor != null)
                neighbor.setBlock(x, y, ChunkUtils.SIZE, block);
            if(x == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, -1);
                if(neighbor != null)
                    neighbor.setBlock(ChunkUtils.SIZE, y, ChunkUtils.SIZE, block);
            }else if(x == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, -1);
                if(neighbor != null)
                    neighbor.setBlock(-1, y, ChunkUtils.SIZE, block);
            }
        }else if(z == ChunkUtils.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, 1);
            if(neighbor != null)
                neighbor.setBlock(x, y, -1, block);
            if(x == 0){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 1);
                if(neighbor != null)
                    neighbor.setBlock(ChunkUtils.SIZE, y, -1, block);
            }else if(x == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 1);
                if(neighbor != null)
                    neighbor.setBlock(-1, y, -1, block);
            }
        }
    }
    
}
