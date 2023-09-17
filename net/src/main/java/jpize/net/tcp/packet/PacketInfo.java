package jpize.net.tcp.packet;

import jpize.util.io.JpizeInputStream;

import java.io.IOException;

public class PacketInfo{
    
    private final byte packetID;
    private final JpizeInputStream dataStream;
    
    public PacketInfo(byte packetID, JpizeInputStream inStream){
        this.packetID = packetID;
        this.dataStream = inStream;
    }
    
    public byte getPacketID(){
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
