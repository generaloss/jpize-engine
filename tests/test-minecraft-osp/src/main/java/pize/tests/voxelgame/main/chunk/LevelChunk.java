package pize.tests.voxelgame.main.chunk;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.main.block.BlockData;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;
import pize.tests.voxelgame.main.chunk.storage.Heightmap;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.main.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.*;

public class LevelChunk{
    
    protected final Level level;
    protected final ChunkPos position;
    protected final LevelChunkSection[] sections;
    protected int highestSectionIndex;
    protected final Map<HeightmapType, Heightmap> heightmaps;

    public LevelChunk(Level level, ChunkPos position){
        this.level = level;
        this.position = position;
        this.sections = new LevelChunkSection[16];
        this.highestSectionIndex = -1;
        
        // Heightmaps
        this.heightmaps = new HashMap<>();
        for(HeightmapType type: HeightmapType.values())
            heightmaps.put(type, new Heightmap(this, type));
    }
    
    public Level getLevel(){
        return level;
    }
    
    
    public short getBlock(int lx, int y, int lz){
        if(isOutOfBounds(lx, y, lz))
            return Blocks.AIR.getDefaultData();
        
        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return Blocks.AIR.getDefaultData();
        
        // Возвращаем блок
        return section.getBlock(lx, getLocalCoord(y), lz);
    }
    
    public boolean setBlock(int lx, int y, int lz, short blockState){
        if(isOutOfBounds(lx, y, lz))
            return false;
        
        // Проверяем является ли устанавливаемый блок воздухом
        final int airID = Blocks.AIR.getID();
        final int blockID = BlockData.getID(blockState);
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
        final int oldBlockID = BlockData.getID(section.getBlock(lx, ly, lz));
        if(blockID == oldBlockID)
            return false;
        
        // Подсчет блоков в секции
        if(isBlockAir)
            section.blocksNum--; // Если блок был заменен воздухом - кол-во блоков в секции уменьшаем на 1
        else
            section.blocksNum++; // Если воздух был заменен блоком - увеличиваем на 1
        
        // УСТАНАВЛИВАЕМ БЛОК
        section.setBlock(lx, ly, lz, blockState);
        return true;
    }
    
    
    public LevelChunkSection[] getSections(){
        return sections;
    }
    
    public LevelChunkSection getSection(int index){
        return sections[index];
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
        return sections[index] = new LevelChunkSection();
    }
    
    public void setSections(LevelChunkSection[] sections, int highestSectionIndex){
        System.arraycopy(sections, 0, this.sections, 0, sections.length);
        this.highestSectionIndex = highestSectionIndex;
    }
    
    
    public BlockProperties getBlockProps(int lx, int y, int lz){
        return BlockData.getProps(getBlock(lx, y, lz));
    }
    
    public byte getBlockID(int lx, int y, int lz){
        return BlockData.getID(getBlock(lx, y, lz));
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
    
    
    public byte getLight(int lx, int y, int lz){
        if(isOutOfBounds(y))
            return 15;
        
        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return 15;
        
        // Возвращаем блок
        return section.getLight(lx, getLocalCoord(y), lz);
    }
    
    public void setLight(int lx, int y, int lz, int level){
        // if(isOutOfBoundsE(lx, y, lz))
        //     return;
        
        // Находим по Y нужную секцию
        final int sectionIndex = getSectionIndex(y);
        LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            section = createSection(sectionIndex);
        
        section.setLight(lx, getLocalCoord(y), lz, level);
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
    
    
    public LevelChunk getNeighbor(int chunkX, int chunkZ){
        return level.getChunkManager().getChunk(position.x + chunkX, position.z + chunkZ);
    }
    
}
