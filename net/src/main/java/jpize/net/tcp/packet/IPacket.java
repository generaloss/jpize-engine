package jpize.net.tcp.packet;

import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public abstract class IPacket<H extends PacketHandler>{
    
    private final short packetID;
    
    public IPacket(int packetID){
        this.packetID = (byte) packetID;
    }
    
    
    public short getPacketID(){
        return packetID;
    }
    
    abstract public void write(JpizeOutputStream stream) throws IOException; // For a sender
    
    abstract public void read(JpizeInputStream stream) throws IOException; // For a receiver
    
    abstract public void handle(H handler);
    
}
