package jpize.net.tcp.packet;

import jpize.util.io.JpizeInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Packets{
    
    public static PacketInfo getPacketInfo(byte[] data){
        try{
            if(data.length < 2)
                return null;
            
            final JpizeInputStream stream = new JpizeInputStream(new ByteArrayInputStream(data));
            int packetID = stream.readByte();
            
            return new PacketInfo(packetID, stream);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
}
