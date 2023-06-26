package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.base.entity.Entity;

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
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
    }
    
}