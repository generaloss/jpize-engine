package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUTF(reasonComponent);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        reasonComponent = stream.readUTF();
    }
    
}
