package pize.tests.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class EncodePacket extends IPacket{
    
    public static final int PACKET_TYPE_ID = 83;
    
    public EncodePacket(){
        super(PACKET_TYPE_ID);
    }
    
    
    private byte[] key;
    
    public EncodePacket(byte[] key){
        this();
        this.key = key;
    }
    
    public byte[] getKey(){
        return key;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeByteArray(key);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        key = stream.readByteArray();
    }
    
}
