package pize.net.tcp.packet;

import pize.net.tcp.TcpConnection;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public abstract class IPacket<L extends PacketHandler>{
    
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
    
    abstract protected void write(PizeOutputStream stream) throws IOException; // For a sender
    
    abstract public void read(PizeInputStream stream) throws IOException; // For a receiver
    
    public void handle(L packetListener){ }
    
}
