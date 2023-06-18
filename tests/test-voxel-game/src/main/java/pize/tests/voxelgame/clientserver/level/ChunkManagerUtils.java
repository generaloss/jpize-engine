package pize.tests.voxelgame.clientserver.level;

import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.server.chunk.ServerChunk;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.*;

public class ChunkManagerUtils{
    
    public static void newThread(Runnable runnable, String name){
        final Thread thread = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                runnable.run();
                Thread.yield();
            }
        }, name + " Thread");
        
        thread.setDaemon(true);
        thread.start();
    }
    
    
    public static void updateNeighborChunksEdgesAndSelf(ServerChunk chunk, boolean loaded){
        // neighbors
        ServerChunk neighbor = getNeighborChunk(chunk, -1, 0);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, SIZE, i, 0, i, -1, i, SIZE_IDX, i);
        
        neighbor = getNeighborChunk(chunk, 1, 0);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, -1, i, SIZE_IDX, i, SIZE, i, 0, i);
        
        neighbor = getNeighborChunk(chunk, 0, -1);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, i, SIZE, i, 0, i, -1, i, SIZE_IDX);
        
        neighbor = getNeighborChunk(chunk, 0, 1);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, i, -1, i, SIZE_IDX, i, SIZE, i, 0);
        
        // corners
        neighbor = getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, -1, 0, SIZE_IDX, -1, SIZE, SIZE_IDX, 0);
        
        neighbor = getNeighborChunk(chunk, 1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, -1, -1, SIZE_IDX, SIZE_IDX, SIZE, SIZE, 0, 0);
        
        neighbor = getNeighborChunk(chunk, -1, -1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, SIZE, 0, 0, -1, -1, SIZE_IDX, SIZE_IDX);
        
        neighbor = getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, -1, 0, SIZE_IDX, -1, SIZE, SIZE_IDX, 0);
    }
    
    public static void updateNeighborChunksEdgesAndSelf(ClientChunk chunk, boolean loaded){
        // neighbors
        ClientChunk neighbor = getNeighborChunk(chunk, -1, 0);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, SIZE, i, 0, i, -1, i, SIZE_IDX, i);
        
        neighbor = getNeighborChunk(chunk, 1, 0);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, -1, i, SIZE_IDX, i, SIZE, i, 0, i);
        
        neighbor = getNeighborChunk(chunk, 0, -1);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, i, SIZE, i, 0, i, -1, i, SIZE_IDX);
        
        neighbor = getNeighborChunk(chunk, 0, 1);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, i, -1, i, SIZE_IDX, i, SIZE, i, 0);
        
        // corners
        neighbor = getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, -1, 0, SIZE_IDX, -1, SIZE, SIZE_IDX, 0);
        
        neighbor = getNeighborChunk(chunk, 1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, -1, -1, SIZE_IDX, SIZE_IDX, SIZE, SIZE, 0, 0);
        
        neighbor = getNeighborChunk(chunk, -1, -1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, SIZE, 0, 0, -1, -1, SIZE_IDX, SIZE_IDX);
        
        neighbor = getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, -1, 0, SIZE_IDX, -1, SIZE, SIZE_IDX, 0);
    }
    
    public static void updateEdge(int y, boolean loaded, Chunk chunk1, Chunk chunk2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        chunk2.setBlock(x1, y, y1, loaded ? chunk1.getBlock(x2, y, y2) : Block.VOID_AIR.getState(), true);
        
        if(loaded)
            chunk1.setBlock(x3, y, y3, chunk2.getBlock(x4, y, y4), true);
    }
    
    public static float distToChunk(int x, int z, Vec3f pos){
        return Vec2f.len(x - pos.x / SIZE + 0.5F, z - pos.z / SIZE + 0.5F);
    }
    
    public static float distToChunk(int x, int z, Vec2f pos){
        return Vec2f.len(x - pos.x / SIZE + 0.5F, z - pos.y / SIZE + 0.5F);
    }
    
}
