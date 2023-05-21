package pize.tests.net;

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
            final DataOutputStream channelStream = channel.getOutputStream();
            
            final ByteArrayOutputStream byteDataStream = new ByteArrayOutputStream();
            final DataOutputStream packetWriter = new DataOutputStream(byteDataStream);
            
            packetWriter.writeByte(packetTypeID); // Write a packet type
            this.write(packetWriter); // Write bytes in 'buffer' stream
            
            channelStream.writeInt(byteDataStream.size()); // Write data size (TcpByteChannel feature)
            byteDataStream.writeTo(channelStream); // Write 'buffer' stream
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    abstract protected void write(DataOutputStream stream) throws IOException; // For a sender
    
    abstract public void read(DataInputStream stream) throws IOException; // For a receiver
    
}
