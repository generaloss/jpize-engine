package pize.net.tcp.packet;

import pize.net.tcp.TcpChannel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class IPacket{
    
    private final byte packetTypeID;
    
    public IPacket(int packetTypeID){
        this.packetTypeID = (byte) packetTypeID;
    }
    
    
    public byte getTypeID(){
        return packetTypeID;
    }
    
    public void write(TcpChannel channel){
        channel.send(dataStream->{
            try{
                dataStream.writeByte(packetTypeID);
                write(dataStream);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }
    
    abstract protected void write(DataOutputStream stream) throws IOException; // For a sender
    
    
    abstract public void read(DataInputStream stream) throws IOException; // For a receiver
    
}
