package pize.net.tcp.packet;

import pize.net.tcp.TcpChannel;

import java.io.IOException;

public abstract class IPacket{
    
    private final byte packetID;
    
    public IPacket(int packetID){
        this.packetID = (byte) packetID;
    }
    
    
    public byte getTypeID(){
        return packetID;
    }
    
    public synchronized void write(TcpChannel channel){
        channel.send(dataStream->{
            try{
                dataStream.writeByte(packetID);
                write(dataStream);
                // System.out.println("packet " + packetID + " size: " + dataStream.size());
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }
    
    abstract protected void write(PacketOutputStream stream) throws IOException; // For a sender
    
    
    abstract public void read(PacketInputStream stream) throws IOException; // For a receiver
    
}
