package pize.tests.net.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Utils{
    
    public static int packetTypeID;
    public static DataInputStream stream;
    
    public static void receive(byte[] data){
        try{
            if(data.length < 2){
                System.out.println("   RECEIVED NOT PACKET.");
                return;
            }
            
            if(stream != null)
                stream.close();
            stream = new DataInputStream(new ByteArrayInputStream(data));
            
            packetTypeID = stream.readByte();
            
            // System.out.println("   RECEIVED DATA: dataSize: " + data.length + ", ?packetID: " + data[0]);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
}
