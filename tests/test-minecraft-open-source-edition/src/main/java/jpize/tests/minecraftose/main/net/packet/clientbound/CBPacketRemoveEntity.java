package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.tests.minecraftose.main.entity.Entity;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class CBPacketRemoveEntity extends IPacket<ClientConnection>{
    
    public static final byte PACKET_ID = 16;
    
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

    @Override
    public void handle(ClientConnection handler){
        handler.removeEntity(this);
    }
    
}