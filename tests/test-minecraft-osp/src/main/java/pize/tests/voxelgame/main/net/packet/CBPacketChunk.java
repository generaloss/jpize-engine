package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.chunk.LevelChunkSection;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;
import pize.tests.voxelgame.main.chunk.storage.Heightmap;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.client.level.ClientLevel;

import java.io.IOException;
import java.util.*;

public class CBPacketChunk extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 2;
    
    
    private ChunkPos position;
    private LevelChunkSection[] sections;
    private int highestSectionIndex;
    private Collection<Heightmap> heightmapsToWrite;
    private Map<HeightmapType, short[]> readHeightmaps;
    
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
    }
    
    
    public ClientChunk getChunk(ClientLevel level){
        final ClientChunk chunk = new ClientChunk(level, position);
        chunk.setSections(sections, highestSectionIndex);
        chunk.setHeightmaps(readHeightmaps);
        return chunk;
    }
    
    
    @Override
    protected void write(PizeOutputStream stream) throws IOException{
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
    }
    
    private void writeSection(PizeOutputStream stream, int sectionIndex) throws IOException{
        final LevelChunkSection section = sections[sectionIndex];
        
        stream.writeByte(sectionIndex); // index
        stream.writeShort(section.blocksNum); // blocks num
        stream.writeShortArray(section.blocks); // blocks data
        stream.writeByteArray(section.light); // light data
    }
    
    private void writeHeightmap(PizeOutputStream stream, Heightmap heightmap) throws IOException{
        stream.writeByte(heightmap.getType().ordinal());
        stream.writeShortArray(heightmap.getValues());
    }
    
    
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
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
    }
    
    private void readSection(PizeInputStream stream) throws IOException{
        final byte sectionIndex = stream.readByte(); // index
        final short blocksNum = stream.readShort(); // blocks num
        final short[] blocks = stream.readShortArray(); // blocks data
        final byte[] light = stream.readByteArray(); // light data
        
        final LevelChunkSection section = new LevelChunkSection(blocks, light);
        section.blocksNum = blocksNum;
        sections[sectionIndex] = section;
    }
    
    private void readHeightmap(PizeInputStream stream) throws IOException{
        final HeightmapType type = HeightmapType.values()[stream.readByte()];
        final short[] values = stream.readShortArray();
        
        readHeightmaps.put(type, values);
    }
    
}
