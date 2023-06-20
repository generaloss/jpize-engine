package pize.tests.voxelgame.clientserver.chunk;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.*;

public class LevelChunkSection{
    
    public final short[] blocks;
    public int blocksNum;
    
    public LevelChunkSection(short[] blocks){
        this.blocks = blocks;
    }
    
    public LevelChunkSection(){
        this(new short[C_VOLUME]);
    }
    
    
    public short getBlock(int lx, int ly, int lz){
        return blocks[getIndexC(lx, ly, lz)];
    }
    
    protected void setBlock(int lx, int ly, int lz, short blockState){
        blocks[getIndexC(lx, ly, lz)] = blockState;
    }
    
    
    public int getBlocksNum(){
        return blocksNum;
    }

}
