package glit.tests.minecraft.client.world.chunk.utils;

import glit.tests.minecraft.client.world.chunk.ChunkSection;
import glit.tests.minecraft.client.world.chunk.storage.ChunkBiomeContainer;
import glit.tests.minecraft.client.world.chunk.storage.ChunkHeightMap;
import glit.tests.minecraft.client.world.block.BlockPos;
import glit.tests.minecraft.client.world.block.BlockState;

public abstract class IChunk{


    abstract public BlockState getBlockState(int x,int y,int z);

    abstract public void setBlockState(int x,int y,int z,BlockState state);

    abstract public BlockState getBlockState(BlockPos pos);

    abstract public void setBlockState(BlockPos pos,BlockState state);


    abstract public ChunkPos getPos();

    abstract public ChunkSection[] getSections();

    abstract public ChunkHeightMap getHeightMap();

    abstract public ChunkBiomeContainer getBiomes();


    abstract public void setModified(boolean modified);

    abstract public boolean isModified();


    public static int WIDTH = 16;
    public static int SECTIONS = 16;
    public static int HEIGHT = SECTIONS * ChunkSection.HEIGHT;
    public static int WIDTH1 = WIDTH - 1;
    public static int HEIGHT1 = HEIGHT - 1;
    public static int AREA = WIDTH * WIDTH;
    public static int WIDTH_BITS = Integer.highestOneBit(WIDTH);
    public static int WIDTH_BITS2 = WIDTH_BITS * 2;


}
