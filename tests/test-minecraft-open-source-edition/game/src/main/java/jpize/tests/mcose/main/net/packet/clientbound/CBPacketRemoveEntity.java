package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.tests.mcose.main.entity.Entity;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class CBPacketRemoveEntity extends IPacket<ClientPacketHandler>{
    
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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.removeEntity(this);
    }
    
}