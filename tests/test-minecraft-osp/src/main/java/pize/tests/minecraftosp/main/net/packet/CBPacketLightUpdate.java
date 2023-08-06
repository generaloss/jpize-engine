package pize.tests.minecraftosp.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;
import pize.tests.minecraftosp.main.chunk.LevelChunkSection;
import pize.tests.minecraftosp.main.chunk.storage.SectionPos;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public class CBPacketLightUpdate extends IPacket<PacketHandler>{

    public static final int PACKET_ID = 24;

    public CBPacketLightUpdate(){
        super(PACKET_ID);
    }

    public SectionPos position;
    public byte[] light;

    public CBPacketLightUpdate(LevelChunkSection section){
        this();
        position = section.getPosition();
        light = section.light;
    }


    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeVec3i(position);
        stream.write(light);
    }

    @Override
    public void read(PizeInputStream stream) throws IOException{
        position = new SectionPos(stream.readVec3i());
        light = stream.readNBytes(ChunkUtils.VOLUME);
    }

}