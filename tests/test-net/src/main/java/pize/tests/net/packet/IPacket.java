package pize.tests.net.packet;

import pize.net.tcp.TcpByteChannel;

import java.io.ByteArrayOutputStream;
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
    
    public void write(TcpByteChannel channel){
        try{
            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            final DataOutputStream dataStream = new DataOutputStream(byteStream);
            
            // write packet
            dataStream.writeByte(packetTypeID);
            write(dataStream);
            
            // write data
            channel.send(byteStream);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    abstract protected void write(DataOutputStream stream) throws IOException; // For a sender
    
    
    abstract public void read(DataInputStream stream) throws IOException; // For a receiver
    
}
