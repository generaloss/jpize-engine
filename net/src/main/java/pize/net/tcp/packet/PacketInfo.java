package pize.net.tcp.packet;

import java.io.IOException;

public class PacketInfo{
    
    private final int packetID;
    private final PacketInputStream dataStream;
    
    public PacketInfo(int packetID, PacketInputStream inStream){
        this.packetID = packetID;
        this.dataStream = inStream;
    }
    
    public int getPacketID(){
        return packetID;
    }
    
    public <P extends IPacket> P readPacket(P packet){
        try{
            packet.read(dataStream);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return packet;
    }
    
}
