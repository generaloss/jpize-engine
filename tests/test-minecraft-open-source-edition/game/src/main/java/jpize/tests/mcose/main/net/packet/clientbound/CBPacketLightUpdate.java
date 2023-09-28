package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.tests.mcose.main.chunk.ChunkUtils;
import jpize.tests.mcose.main.chunk.LevelChunkSection;
import jpize.tests.mcose.main.chunk.storage.SectionPos;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketLightUpdate extends IPacket<ClientPacketHandler>{

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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeVec3i(position);
        stream.write(light);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        position = new SectionPos(stream.readVec3i());
        light = stream.readNBytes(ChunkUtils.VOLUME);
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.lightUpdate(this);
    }

}