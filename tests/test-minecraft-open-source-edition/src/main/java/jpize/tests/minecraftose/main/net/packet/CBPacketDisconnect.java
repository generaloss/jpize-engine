package jpize.tests.minecraftose.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(reasonComponent);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        reasonComponent = stream.readUTF();
    }
    
}
