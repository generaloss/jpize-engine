package megalul.projectvostok.chunk.data;

import megalul.projectvostok.Main;
import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class ChunkField{
    
    private final Chunk chunkOf;
    
    private final short[] blocks;
    private final HeightDepthMap heightDepthMap;
    
    public ChunkField(Chunk chunkOf){
        this.chunkOf = chunkOf;
        
        heightDepthMap = new HeightDepthMap();
        blocks = new short[C_VOLUME];
    }
    
    
    public short get(int x, int y, int z){
        return blocks[getIndexC(x, y, z)];
    }
    
    public void set(int x, int y, int z, short block){
        byte oldID = getID(x, y, z);
        byte id = BlockState.getID(block);
        
        blocks[getIndexC(x, y, z)] = block;
        
        if(oldID != id && !isOutOfBounds(x, z)){
            chunkOf.getProvider().rebuildChunk(chunkOf);
            
            updateEdgesOfNeighborChunks(x, y, z, block);
            
            updateHeight(x, y, z, id != 0);
            if(Main.UPDATE_DEPTH_MAP)
                updateDepth(x, y, z, id != 0);
        }
    }
    
    public void fastSet(int x, int y, int z, short block){
        byte oldID = getID(x, y, z);
        blocks[getIndexC(x, y, z)] = block;
    
        if(oldID != BlockState.getID(block) && !isOutOfBounds(x, z))
            updateEdgesOfNeighborChunks(x, y, z, block);
    }
    
    public void updateHeightDepthMap(){
        for(int x = 0; x < SIZE; x++)
            for(int z = 0; z < SIZE; z++){
                for(int y = HEIGHT_IDX; y >= 0; y--)
                    if(getID(x, y, z) != Block.AIR.id){
                        heightDepthMap.setHeight(x, z, y);
                        break;
                    }
                if(Main.UPDATE_DEPTH_MAP)
                    for(int y = 0; y < HEIGHT; y++)
                        if(getID(x, y, z) != Block.AIR.id){
                            heightDepthMap.setDepth(x, z, y);
                            break;
                        }
            }
    
        chunkOf.getProvider().rebuildChunk(chunkOf);
        
        heightDepthMap.updateMax();
        if(Main.UPDATE_DEPTH_MAP)
            heightDepthMap.updateMin();
    }
    
    
    private void updateHeight(int x, int y, int z, boolean placed){
        int height = heightDepthMap.getHeight(x, z);
        
        if(y == height && !placed)
            for(height--; getID(x, height, z) == Block.AIR.id && height > 0; )
                height--;
        else if(y > height && placed)
            height = y;
        
        heightDepthMap.setHeight(x, z, height);
        heightDepthMap.updateMax();
    }
    
    private void updateDepth(int x, int y, int z, boolean placed){
        int depth = heightDepthMap.getDepth(x, z);
        
        if(y == depth && !placed)
            for(depth++; getID(x, depth, z) == Block.AIR.id && depth < HEIGHT_IDX; )
                depth++;
        else if(y < depth && placed)
            depth = y;
        
        heightDepthMap.setDepth(x, z, depth);
        heightDepthMap.updateMin();
    }
    
    public byte getID(int x, int y, int z){
        return BlockState.getID(get(x, y, z));
    }
    
    
    private void updateEdgesOfNeighborChunks(int x, int y, int z, short block){
        Chunk neighbor;
        if(x == 0){
            neighbor = getNeighbor(-1, 0);
            if(neighbor != null){
                neighbor.setBlock(SIZE, y, z, block);
                chunkOf.getProvider().rebuildChunk(neighbor);
            }
            if(z == 0){
                neighbor = getNeighbor(-1, -1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, SIZE, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighbor(-1, 1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, -1, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }
        }else if(x == SIZE_IDX){
            neighbor = getNeighbor(1, 0);
            if(neighbor != null){
                neighbor.setBlock(-1, y, z, block);
                chunkOf.getProvider().rebuildChunk(neighbor);
            }
            if(z == 0){
                neighbor = getNeighbor(1, -1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, SIZE, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }else if(z == SIZE_IDX){
                neighbor = getNeighbor(1, 1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, -1, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }
        }
        
        if(z == 0){
            neighbor = getNeighbor(0, -1);
            if(neighbor != null){
                neighbor.setBlock(x, y, SIZE, block);
                chunkOf.getProvider().rebuildChunk(neighbor);
            }
            if(x == 0){
                neighbor = getNeighbor(-1, -1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, SIZE, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighbor(1, -1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, SIZE, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }
        }else if(z == SIZE_IDX){
            neighbor = getNeighbor(0, 1);
            if(neighbor != null){
                neighbor.setBlock(x, y, -1, block);
                chunkOf.getProvider().rebuildChunk(neighbor);
            }
            if(x == 0){
                neighbor = getNeighbor(-1, 1);
                if(neighbor != null){
                    neighbor.setBlock(SIZE, y, -1, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }else if(x == SIZE_IDX){
                neighbor = getNeighbor(1, 1);
                if(neighbor != null){
                    neighbor.setBlock(-1, y, -1, block);
                    chunkOf.getProvider().rebuildChunk(neighbor);
                }
            }
        }
    }
    
    private Chunk getNeighbor(int x, int z){
        return chunkOf.getProvider().getChunk(chunkOf.getPos().getNeighbor(x, z));
    }
    
    
    public HeightDepthMap getHeightDepthMap(){
        return heightDepthMap;
    }
    
}
