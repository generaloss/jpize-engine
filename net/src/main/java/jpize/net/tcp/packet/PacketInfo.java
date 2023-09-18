package jpize.net.tcp.packet;

import jpize.util.io.JpizeInputStream;

import java.io.IOException;

public class PacketInfo{
    
    private final short packetID;
    private final JpizeInputStream dataStream;
    
    public PacketInfo(short packetID, JpizeInputStream inStream){
        this.packetID = packetID;
        this.dataStream = inStream;
    }
    
    public short getPacketID(){
        return packetID;
    }
    
    public <P extends IPacket<?>> P readPacket(P packet){
        try{
            packet.read(dataStream);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return packet;
    }
    
}
