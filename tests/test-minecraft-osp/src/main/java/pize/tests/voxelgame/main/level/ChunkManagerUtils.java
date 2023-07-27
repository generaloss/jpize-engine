package pize.tests.voxelgame.main.level;

import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.chunk.ClientChunk;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.*;

public class ChunkManagerUtils{
    
    public static void rebuildNeighborChunks(ClientChunk chunk){
        ClientChunk neighbor;
        
        neighbor = getNeighborChunk(chunk, -1, 0);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = getNeighborChunk(chunk, -1, -1);
        if(neighbor != null)
            neighbor.rebuild(false);
   
        neighbor = getNeighborChunk(chunk, 1, 0);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        
        neighbor = getNeighborChunk(chunk, 1, 1);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = getNeighborChunk(chunk, 1, -1);
        if(neighbor != null)
            neighbor.rebuild(false);
            
        neighbor = getNeighborChunk(chunk, 0, 1);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = getNeighborChunk(chunk, 0, -1);
        if(neighbor != null)
            neighbor.rebuild(false);
    }
    
    public static void rebuildNeighborChunks(ClientChunk chunk, int lx, int lz){
        ClientChunk neighbor;
        
        if(lx == 0){
            neighbor = getNeighborChunk(chunk, -1, 0);
            if(neighbor != null)
                neighbor.rebuild(false);
            
            if(lz == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, -1, 1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }else if(lz == 0 ){
                neighbor = getNeighborChunk(chunk, -1, -1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }
        }else if(lx == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 1, 0);
            if(neighbor != null)
                neighbor.rebuild(false);
            
            if(lz == SIZE_IDX){
                neighbor = getNeighborChunk(chunk, 1, 1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }else if(lz == 0 ){
                neighbor = getNeighborChunk(chunk, 1, -1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }
        }
        
        if(lz == SIZE_IDX){
            neighbor = getNeighborChunk(chunk, 0, 1);
            if(neighbor != null)
                neighbor.rebuild(false);
        }else if(lz == 0){
            neighbor = getNeighborChunk(chunk, 0, -1);
            if(neighbor != null)
                neighbor.rebuild(false);
        }
    }
    
    
    public static float distToChunk(int x, int z, Vec3f pos){
        return Vec2f.len(x - pos.x / SIZE + 0.5F, z - pos.z / SIZE + 0.5F);
    }
    
    public static float distToChunk(int x, int z, Vec2f pos){
        return Vec2f.len(x - pos.x / SIZE + 0.5F, z - pos.y / SIZE + 0.5F);
    }
    
}
