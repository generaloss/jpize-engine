package jpize.tests.minecraftose.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.minecraftose.main.entity.Entity;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class CBPacketRemoveEntity extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 16;
    
    public CBPacketRemoveEntity(){
        super(PACKET_ID);
    }
    
    
    public UUID uuid;
    
    public CBPacketRemoveEntity(Entity entity){
        this();
        uuid = entity.getUUID();
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
    }
    
}