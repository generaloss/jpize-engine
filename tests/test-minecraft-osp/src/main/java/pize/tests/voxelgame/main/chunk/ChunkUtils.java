package pize.tests.voxelgame.main.chunk;

import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.server.chunk.ServerChunk;

public class ChunkUtils{

    public static final int SIZE_SHIFT = 4; // Степень двойки
    
    public static final int SIZE = 1 << SIZE_SHIFT; // SIZE = 2 ^ SIZE_SHIFT
    public static final int SIZE_IDX = SIZE - 1;
    
    public static final int HEIGHT = 256;
    public static final int HEIGHT_IDX = HEIGHT - 1;
    
    public static final int AREA = SIZE * SIZE;
    public static final int VOLUME = AREA * SIZE;
    
    // Light
    public static final int MAX_LIGHT_LEVEL = 15;
    
    
    public static int getIndex(int x, int y, int z){
        return x + z * SIZE + y * AREA;
    }

    public static int getIndex(int x, int z){
        return x + z * SIZE;
    }
    
    public static int getSectionIndex(int y){
        return getChunkPos(y);
    }
    
    public static int getSectionY(int index){
        return index << SIZE_SHIFT;
    }
    
    public static int getLocalCoord(int xyz){
        return xyz & SIZE_IDX;
    }
    
    public static int getChunkPos(int XorZ){
        return XorZ >> SIZE_SHIFT;
    }


    public static boolean isOutOfBounds(int x, int y, int z){
        return x > SIZE_IDX || y > HEIGHT_IDX || z > SIZE_IDX || x < 0 || y < 0 || z < 0;
    }
    
    public static boolean isOutOfBounds(int x, int z){
        return x > SIZE_IDX || z > SIZE_IDX || x < 0 || z < 0;
    }
    
    public static boolean isOutOfBounds(int y){
        return y > HEIGHT_IDX || y < 0;
    }
    
    
    public static LevelChunk getNeighborChunk(LevelChunk chunk, int x, int z){
        return chunk.getLevel().getChunkManager().getChunk(chunk.getPosition().getNeighbor(x, z));
    }
    
    public static ClientChunk getNeighborChunk(ClientChunk chunk, int x, int z){
        return chunk.getLevel().getChunkManager().getChunk(chunk.getPosition().getNeighbor(x, z));
    }
    
    public static ServerChunk getNeighborChunk(ServerChunk chunk, int x, int z){
        return chunk.getLevel().getChunkManager().getChunk(chunk.getPosition().getNeighbor(x, z));
    }

}
