package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.main.entity.Entity;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
    }
    
}