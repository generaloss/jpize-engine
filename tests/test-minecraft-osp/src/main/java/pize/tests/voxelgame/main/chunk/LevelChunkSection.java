package pize.tests.voxelgame.main.chunk;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.VOLUME;
import static pize.tests.voxelgame.main.chunk.ChunkUtils.getIndex;

public class LevelChunkSection{
    
    public final short[] blocks;
    public final byte[] light;
    public int blocksNum;
    
    public LevelChunkSection(short[] blocks, byte[] light){
        this.blocks = blocks;
        this.light = light;
    }
    
    public LevelChunkSection(){
        this(
            new short[VOLUME],
            new byte[VOLUME]
        );
        // Arrays.fill(light, (byte) 15);
    }
    
    
    public short getBlock(int lx, int ly, int lz){
        return blocks[getIndex(lx, ly, lz)];
    }
    
    protected void setBlock(int lx, int ly, int lz, short blockState){
        blocks[getIndex(lx, ly, lz)] = blockState;
    }
    
    
    public byte getLight(int lx, int ly, int lz){
        return light[getIndex(lx, ly, lz)];
    }
    
    protected void setLight(int lx, int ly, int lz, int level){
        light[getIndex(lx, ly, lz)] = (byte) level;
    }
    
    
    public int getBlocksNum(){
        return blocksNum;
    }

}
