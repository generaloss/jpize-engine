package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.tests.minecraftose.main.chunk.ChunkUtils;
import jpize.tests.minecraftose.main.chunk.LevelChunkSection;
import jpize.tests.minecraftose.main.chunk.storage.SectionPos;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketLightUpdate extends IPacket<ClientConnection>{

    public static final byte PACKET_ID = 24;

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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeVec3i(position);
        stream.write(light);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        position = new SectionPos(stream.readVec3i());
        light = stream.readNBytes(ChunkUtils.VOLUME);
    }

    @Override
    public void handle(ClientConnection handler){
        handler.lightUpdate(this);
    }

}