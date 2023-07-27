package pize.tests.voxelgame.main.chunk.storage;

import java.util.Objects;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.SIZE;

public class ChunkPos{

    public final int x, z;

    public ChunkPos(int x, int z){
        this.x = x;
        this.z = z;
    }
    

    public ChunkPos getNeighbor(int x, int z){
        return new ChunkPos(this.x + x, this.z + z);
    }
    
    public int globalX(){
        return x * SIZE;
    }
    
    public int globalZ(){
        return z * SIZE;
    }


    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null || object.getClass() != getClass())
            return false;
        ChunkPos chunkPos = (ChunkPos) object;
        return x == chunkPos.x && z == chunkPos.z;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, z);
    }
    
    @Override
    public String toString(){
        return x + ", " + z;
    }

}
