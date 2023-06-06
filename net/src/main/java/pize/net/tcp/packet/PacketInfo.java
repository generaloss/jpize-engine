package pize.net.tcp.packet;

import java.io.DataInputStream;
import java.io.IOException;

public class PacketInfo{
    
    private final int packetID;
    private final DataInputStream dataStream;
    
    public PacketInfo(int packetID, DataInputStream dataStream){
        this.packetID = packetID;
        this.dataStream = dataStream;
    }
    
    public int getPacketID(){
        return packetID;
    }
    
    public <P extends IPacket> P readPacket(P packet) throws IOException{
        packet.read(dataStream);
        return packet;
    }
    
}
