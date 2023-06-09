package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketDisconnect extends IPacket{
    
    public static final int PACKET_ID = 3;
    
    public PacketDisconnect(){
        super(PACKET_ID);
    }
    
    
    public String reasonComponent;
    
    public PacketDisconnect(String reasonComponent){
        this();
        this.reasonComponent = reasonComponent;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
    
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
    
    }
    
}
