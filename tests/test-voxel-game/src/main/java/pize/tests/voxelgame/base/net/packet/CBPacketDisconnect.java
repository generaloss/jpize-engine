package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class CBPacketDisconnect extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 3;
    
    public CBPacketDisconnect(){
        super(PACKET_ID);
    }
    
    
    public String reasonComponent;
    
    public CBPacketDisconnect(String reasonComponent){
        this();
        this.reasonComponent = reasonComponent;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(reasonComponent);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        reasonComponent = stream.readUTF();
    }
    
}
