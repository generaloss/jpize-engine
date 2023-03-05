package glit.tests.minecraft.client.world.chunk;

import glit.tests.minecraft.client.world.chunk.storage.ChunkBiomeContainer;
import glit.tests.minecraft.client.world.block.BlockPos;
import glit.tests.minecraft.client.world.block.BlockState;
import glit.tests.minecraft.client.world.chunk.storage.ChunkHeightMap;
import glit.tests.minecraft.client.world.chunk.utils.ChunkPos;
import glit.tests.minecraft.client.world.chunk.utils.IChunk;

public class Chunk extends IChunk{

    private final ChunkPos position;

    private final ChunkSection[] sections;
    private final ChunkHeightMap heightMap;
    private final ChunkBiomeContainer biomes;

    private boolean modified;

    public Chunk(int x,int z){
        position = new ChunkPos(x,z);

        sections = new ChunkSection[SECTIONS];
        heightMap = new ChunkHeightMap(this);
        biomes = new ChunkBiomeContainer();

        modified = true;
    }

    public Chunk(ChunkPos position){
        this(position.x,position.y);
    }


    @Override
    public BlockState getBlockState(int x,int y,int z){
        int normX = x & WIDTH1;
        int normY = y & HEIGHT1;
        int normZ = z & WIDTH1;

        return sections[normY % SECTIONS].getState(normX, y % SECTIONS, normZ);
    }

    @Override
    public void setBlockState(int x,int y,int z,BlockState state){
        int normX = x & WIDTH1;
        int normY = y & HEIGHT1;
        int normZ = z & WIDTH1;

        int sectionY = normY << ChunkSection.HEIGHT_BITS;
        ChunkSection section = sections[sectionY];
        if(section == null){
            if(state.isEmpty())
                return;

            section = new ChunkSection(sectionY);
            sections[sectionY] = section;
        }

        section.setState(normX, normY % SECTIONS, normZ, state);
        modified = true;
    }

    @Override
    public BlockState getBlockState(BlockPos pos){
        return getBlockState(pos.x, pos.y, pos.z);
    }

    @Override
    public void setBlockState(BlockPos pos,BlockState state){
        setBlockState(pos.x, pos.y, pos.z, state);
    }


    @Override
    public ChunkPos getPos(){
        return position;
    }

    @Override
    public ChunkSection[] getSections(){
        return sections;
    }

    @Override
    public ChunkHeightMap getHeightMap(){
        return heightMap;
    }

    @Override
    public ChunkBiomeContainer getBiomes(){
        return biomes;
    }


    @Override
    public void setModified(boolean modified){
        this.modified = modified;
    }

    @Override
    public boolean isModified(){
        return modified;
    }

}