package jpize.tests.minecraftosp.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.minecraftosp.client.chunk.ClientChunk;
import jpize.tests.minecraftosp.client.level.ClientLevel;
import jpize.tests.minecraftosp.main.biome.Biome;
import jpize.tests.minecraftosp.main.chunk.ChunkUtils;
import jpize.tests.minecraftosp.main.chunk.LevelChunk;
import jpize.tests.minecraftosp.main.chunk.LevelChunkSection;
import jpize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import jpize.tests.minecraftosp.main.chunk.storage.Heightmap;
import jpize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import jpize.tests.minecraftosp.main.chunk.storage.SectionPos;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.*;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.AREA;

public class CBPacketChunk extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 2;
    
    
    private ChunkPos position;
    private LevelChunkSection[] sections;
    private int highestSectionIndex;
    private Collection<Heightmap> heightmapsToWrite;
    private Map<HeightmapType, short[]> readHeightmaps;
    private Biome[] biomes;
    
    public CBPacketChunk(){
        super(PACKET_ID);
        
        readHeightmaps = new HashMap<>();
    }
    
    public CBPacketChunk(LevelChunk chunk){
        super(PACKET_ID);
        
        position = chunk.getPosition();
        sections = chunk.getSections();
        highestSectionIndex = chunk.getHighestSectionIndex();
        heightmapsToWrite = chunk.getHeightmaps();
        biomes = chunk.getBiomes().getValues();
    }
    
    
    public ClientChunk getChunk(ClientLevel level){
        final ClientChunk chunk = new ClientChunk(level, position);
        chunk.setSections(sections, highestSectionIndex);
        chunk.setHeightmaps(readHeightmaps);
        chunk.updateMaxY();
        chunk.getBiomes().setValues(biomes);
        return chunk;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        // Chunk position
        stream.writeInt(position.x);
        stream.writeInt(position.z);
        
        // Sections header
        long sectionsNum = Arrays.stream(sections).filter(Objects::nonNull).count();
        stream.writeByte((byte) sectionsNum);
        stream.writeByte((byte) highestSectionIndex);
        
        // Sections data
        for(int i = 0; i < sections.length; i++)
            if(sections[i] != null)
                writeSection(stream, i);
        
        // Heightmaps
        stream.writeByte((byte) heightmapsToWrite.size());
        
        for(Heightmap heightmap: heightmapsToWrite)
            writeHeightmap(stream, heightmap);

        // Biomes
        for(Biome biome: biomes)
            stream.writeByte(biome.ordinal());
    }
    
    private void writeSection(JpizeOutputStream stream, int sectionIndex) throws IOException{
        final LevelChunkSection section = sections[sectionIndex];
        
        stream.writeByte(sectionIndex); // index
        stream.writeShort(section.blocksNum); // blocks num
        stream.writeShortArray(section.blocks); // blocks data
        stream.write(section.light); // light data
    }
    
    private void writeHeightmap(JpizeOutputStream stream, Heightmap heightmap) throws IOException{
        stream.writeByte(heightmap.getType().ordinal());
        stream.writeShortArray(heightmap.getValues());
    }
    
    
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        // Chunk position
        position = new ChunkPos(
            stream.readInt(),
            stream.readInt()
        );
        
        // Sections header
        final byte sectionsNum = stream.readByte();
        highestSectionIndex = stream.readByte();
        
        if(sectionsNum == 0)
            return;
        sections = new LevelChunkSection[16];
        
        // Sections data
        for(int i = 0; i < sectionsNum; i++)
            readSection(stream);
        
        // Heightmaps
        final byte heightmapsNum = stream.readByte();
        for(int i = 0; i < heightmapsNum; i++)
            readHeightmap(stream);

        // Biomes
        biomes = new Biome[AREA];
        for(int i = 0; i < biomes.length; i++)
            biomes[i] = Biome.values()[stream.readByte()];
    }
    
    private void readSection(JpizeInputStream stream) throws IOException{
        final byte sectionIndex = stream.readByte(); // index
        final short blocksNum = stream.readShort(); // blocks num
        final short[] blocks = stream.readShortArray(); // blocks data
        final byte[] light = stream.readNBytes(ChunkUtils.VOLUME); // light data
        
        final LevelChunkSection section = new LevelChunkSection(
            new SectionPos(position.x, sectionIndex, position.z),
            blocks,
            light
        );
        section.blocksNum = blocksNum;
        sections[sectionIndex] = section;
    }
    
    private void readHeightmap(JpizeInputStream stream) throws IOException{
        final HeightmapType type = HeightmapType.values()[stream.readByte()];
        final short[] values = stream.readShortArray();
        
        readHeightmaps.put(type, values);
    }
    
}
