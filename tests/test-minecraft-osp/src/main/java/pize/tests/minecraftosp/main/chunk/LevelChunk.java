package pize.tests.minecraftosp.main.chunk;

import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.client.block.BlockProps;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.biome.BiomeMap;
import pize.tests.minecraftosp.main.block.BlockData;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.main.chunk.storage.Heightmap;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.main.chunk.storage.SectionPos;
import pize.tests.minecraftosp.main.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class LevelChunk{

    public static final int NEIGHBORS_RADIUS = MAX_STRUCTURE_SIZE;
    public static final int NEIGHBORS_DIAMETER = NEIGHBORS_RADIUS * 2 + 1;

    protected final Level level;
    protected final ChunkPos position;
    protected final ChunkPos[] neighbors;
    protected final LevelChunkSection[] sections;
    protected int highestSectionIndex;
    protected final Map<HeightmapType, Heightmap> heightmaps;
    protected final BiomeMap biomes;
    private final long spawnTimeMillis;

    public LevelChunk(Level level, ChunkPos position){
        this.level = level;
        this.position = position;

        this.spawnTimeMillis = System.currentTimeMillis();

        this.neighbors = new ChunkPos[NEIGHBORS_DIAMETER * NEIGHBORS_DIAMETER];
        for(int i = 0; i < neighbors.length; i++){
            final int x = i % NEIGHBORS_DIAMETER;
            final int z = (i - x) / NEIGHBORS_DIAMETER;

            neighbors[i] = position.getNeighbor(x - NEIGHBORS_RADIUS, z - NEIGHBORS_RADIUS);
        }
        this.sections = new LevelChunkSection[16];
        this.highestSectionIndex = -1;
        
        // Heightmaps
        this.heightmaps = new HashMap<>();
        for(HeightmapType type: HeightmapType.values())
            heightmaps.put(type, new Heightmap(this, type));

        this.biomes = new BiomeMap();
    }
    
    public Level getLevel(){
        return level;
    }


    public long getLifeTimeMillis(){
        return System.currentTimeMillis() - spawnTimeMillis;
    }
    
    
    public short getBlockState(int lx, int y, int lz){
        if(isOutOfBounds(lx, y, lz))
            return Blocks.AIR.getDefaultData();
        
        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return Blocks.AIR.getDefaultData();
        
        // Возвращаем блок
        return section.getBlockState(lx, getLocalCoord(y), lz);
    }
    
    public boolean setBlockState(int lx, int y, int lz, short blockData){
        if(isOutOfBounds(lx, y, lz))
            return false;
        
        // Проверяем является ли устанавливаемый блок воздухом
        final int airID = Blocks.AIR.getID();
        final int blockID = BlockData.getID(blockData);
        final boolean isBlockAir = (blockID == airID);
        
        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        LevelChunkSection section = getSection(sectionIndex);
        
        if(section != null){
            // Если устанавливаемый блок воздух, и в секции остался один блок
            // - удаляем секцию, чтобы не загружать память секцией с 4096 блоками воздуха, и завершаем метод
            if(section.getBlocksNum() == 1 && isBlockAir && (getSection(sectionIndex - 1) == null || getSection(sectionIndex + 1) == null)){
                removeSection(sectionIndex);
                return true;
            }
            
        // Если секция равна null
        }else{
            // Если устанавливаемый блок воздух - ничего не делаем и завершаем метод
            if(isBlockAir)
                return false;
            
            // Если устанавливаемый блок не воздух - создаем секцию
            section = createSection(sectionIndex);
        }
        
        // Проверка на равенство устанавливаемого блока и текущего
        final int ly = getLocalCoord(y);
        final int oldBlockID = BlockData.getID(section.getBlockState(lx, ly, lz));
        if(blockID == oldBlockID)
            return false;
        
        // Подсчет блоков в секции
        if(isBlockAir)
            section.blocksNum--; // Если блок был заменен воздухом - кол-во блоков в секции уменьшаем на 1
        else
            section.blocksNum++; // Если воздух был заменен блоком - увеличиваем на 1
        
        // УСТАНАВЛИВАЕМ БЛОК
        section.setBlockState(lx, ly, lz, blockData);
        return true;
    }
    
    
    public LevelChunkSection[] getSections(){
        return sections;
    }
    
    public LevelChunkSection getSection(int index){
        return sections[index];
    }

    public LevelChunkSection getBlockSection(int y){
        return getSection(ChunkUtils.getSectionIndex(y));
    }
    
    public int getHighestSectionIndex(){
        return highestSectionIndex;
    }
    
    protected void removeSection(int index){
        sections[index] = null;
        if(highestSectionIndex != index)
            return;
        
        for(int i = sections.length - 1; i >= 0; i--)
            if(getSection(i) != null){
                highestSectionIndex = i;
                return;
            }
        
        highestSectionIndex = -1;
    }
    
    protected LevelChunkSection createSection(int index){
        highestSectionIndex = Math.max(highestSectionIndex, index);
        return sections[index] = new LevelChunkSection(new SectionPos(position.x, index, position.z));
    }
    
    public void setSections(LevelChunkSection[] sections, int highestSectionIndex){
        System.arraycopy(sections, 0, this.sections, 0, sections.length);
        this.highestSectionIndex = highestSectionIndex;
    }


    public Block getBlock(int lx, int y, int lz){
        return BlockData.getBlock(getBlockState(lx, y, lz));
    }

    public BlockProps getBlockProps(int lx, int y, int lz){
        return BlockData.getProps(getBlockState(lx, y, lz));
    }

    public boolean setBlock(int lx, int y, int lz, Block block){
        return setBlockState(lx, y, lz, block.getDefaultData());
    }


    public boolean isEmpty(){
        return highestSectionIndex == -1;
    }
    
    
    public Collection<Heightmap> getHeightmaps(){
        return heightmaps.values();
    }
    
    public void setHeightmaps(Map<HeightmapType, short[]> heightmaps){
        for(Map.Entry<HeightmapType, short[]> heightmapEntry: heightmaps.entrySet()){
            final Heightmap heightmap = new Heightmap(this, heightmapEntry.getKey(), heightmapEntry.getValue());
            this.heightmaps.put(heightmap.getType(), heightmap);
        }
    }
    
    public Heightmap getHeightMap(HeightmapType type){
        return heightmaps.get(type);
    }
    
    public ChunkPos getPosition(){
        return position;
    }
    
    
    public int getSkyLight(int lx, int y, int lz){
        if(isOutOfBounds(y))
            return MAX_LIGHT_LEVEL;
        
        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return MAX_LIGHT_LEVEL;
        
        // Возвращаем блок
        return section.getSkyLight(lx, getLocalCoord(y), lz);
    }

    public int getBlockLight(int lx, int y, int lz){
        if(isOutOfBounds(y))
            return MAX_LIGHT_LEVEL;

        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return MAX_LIGHT_LEVEL;

        // Возвращаем блок
        return section.getBlockLight(lx, getLocalCoord(y), lz);
    }
    
    public void setSkyLight(int lx, int y, int lz, int level){
        if(isOutOfBounds(lx, y, lz))
            return;
        
        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            section = createSection(sectionIndex);
        
        section.setSkyLight(lx, getLocalCoord(y), lz, level);

        // if(getLevel() instanceof ServerLevel serverLevel)
        //     serverLevel.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(section));
    }

    public void setBlockLight(int lx, int y, int lz, int level){
        if(isOutOfBounds(lx, y, lz))
            return;

        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            section = createSection(sectionIndex);

        section.setBlockLight(lx, getLocalCoord(y), lz, level);

        // if(getLevel() instanceof ServerLevel serverLevel)
        //     serverLevel.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(section));
    }


    public BiomeMap getBiomes(){
        return biomes;
    }
    
    
    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null || object.getClass() != getClass())
            return false;
        final LevelChunk chunk = (LevelChunk) object;
        return position.equals(chunk.position);
    }

    @Override
    public int hashCode(){
        return position.hashCode();
    }
    

    public ChunkPos[] getNeighbors(){
        return neighbors;
    }

    public ChunkPos getNeighbor(int signX, int signZ){
        return neighbors[(signZ + NEIGHBORS_RADIUS) * NEIGHBORS_DIAMETER + (signX + NEIGHBORS_RADIUS)];
    }

    public LevelChunk getNeighborChunk(int signX, int signZ){
        return level.getChunkManager().getChunk(getNeighbor(signX, signZ));
    }
    
}
