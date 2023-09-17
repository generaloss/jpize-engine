package jpize.net.tcp.packet;

import jpize.net.tcp.TcpConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public abstract class IPacket<H extends PacketHandler>{
    
    private final byte packetID;
    
    public IPacket(int packetID){
        this.packetID = (byte) packetID;
    }
    
    
    public byte getPacketID(){
        return packetID;
    }
    
    public void write(TcpConnection connection){
        if(connection == null)
            return;
        
        connection.send(dataStream->{
            try{
                dataStream.writeByte(packetID);
                write(dataStream);
                // System.out.println("Write packet " + packetID + " (size: " + dataStream.size() + ")");
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }
    
    abstract protected void write(JpizeOutputStream stream) throws IOException; // For a sender
    
    abstract public void read(JpizeInputStream stream) throws IOException; // For a receiver
    
    abstract public void handle(H handler);
    
}
