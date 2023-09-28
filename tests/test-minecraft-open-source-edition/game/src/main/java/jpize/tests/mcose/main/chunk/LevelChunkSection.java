package jpize.tests.mcose.main.chunk;

import jpize.tests.mcose.main.chunk.storage.SectionPos;

import static jpize.tests.mcose.main.chunk.ChunkUtils.VOLUME;
import static jpize.tests.mcose.main.chunk.ChunkUtils.getIndex;

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
    
    
    public short getBlockState(int lx, int ly, int lz){
        return blocks[getIndex(lx, ly, lz)];
    }
    
    protected void setBlockState(int lx, int ly, int lz, short blockState){
        blocks[getIndex(lx, ly, lz)] = blockState;
    }
    
    
    public int getSkyLight(int lx, int ly, int lz){
        return light[getIndex(lx, ly, lz)] & 0xF;
    }

    protected void setSkyLight(int lx, int ly, int lz, int level){
        final int blockLight = light[getIndex(lx, ly, lz)] >> 4;
        light[getIndex(lx, ly, lz)] = (byte) ((blockLight << 4) | level);
    }

    public int getBlockLight(int lx, int ly, int lz){
        return (light[getIndex(lx, ly, lz)] >> 4) & 0xF;
    }

    protected void setBlockLight(int lx, int ly, int lz, int level){
        final int skyLight = light[getIndex(lx, ly, lz)] & 0xF;
        light[getIndex(lx, ly, lz)] = (byte) ((level << 4) | skyLight);
    }

    
    public int getBlocksNum(){
        return blocksNum;
    }

}
