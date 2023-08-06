package pize.tests.minecraftosp.main.chunk;

import pize.tests.minecraftosp.main.chunk.storage.SectionPos;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.VOLUME;
import static pize.tests.minecraftosp.main.chunk.ChunkUtils.getIndex;

public class LevelChunkSection{

    public final SectionPos position;
    public final short[] blocks;
    public final byte[] light;
    public int blocksNum;
    
    public LevelChunkSection(SectionPos position, short[] blocks, byte[] light){
        this.position = position;
        this.blocks = blocks;
        this.light = light;
    }
    
    public LevelChunkSection(SectionPos position){
        this(
            position,
            new short[VOLUME],
            new byte[VOLUME]
        );
    }


    public SectionPos getPosition(){
        return position;
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
