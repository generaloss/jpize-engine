package jpize.net.tcp.packet;

import jpize.util.io.JpizeInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Packets{
    
    public static PacketInfo getPacketInfo(byte[] data){
        try{
            if(data.length < 5) // int bytes (PACKET_ID) + minimum data bytes  =  4 + 1 bytes
                return null;
            
            final JpizeInputStream stream = new JpizeInputStream(new ByteArrayInputStream(data));
            final int packetID = stream.readInt();
            
            return new PacketInfo(packetID, stream);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
}
