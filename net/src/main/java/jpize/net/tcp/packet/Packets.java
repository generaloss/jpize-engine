package jpize.net.tcp.packet;

import jpize.util.io.JpizeInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Packets{
    
    public static PacketInfo getPacketInfo(byte[] data){
        try{
            if(data.length < 3) // PACKET_ID + minimum data  =  2 + 1 bytes
                return null;
            
            final JpizeInputStream stream = new JpizeInputStream(new ByteArrayInputStream(data));
            final short packetID = stream.readShort();
            
            return new PacketInfo(packetID, stream);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
}
