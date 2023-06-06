package pize.net.tcp.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Packets{
    
    public static PacketInfo getPacketInfo(byte[] data){
        try{
            if(data.length < 2)
                return null;
            
            final DataInputStream stream = new DataInputStream(new ByteArrayInputStream(data));
            int packetID = stream.readByte();
            
            return new PacketInfo(packetID, stream);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    
}
