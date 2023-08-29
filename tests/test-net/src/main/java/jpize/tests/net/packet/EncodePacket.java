package jpize.tests.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeByteArray(key);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        key = stream.readByteArray();
    }
    
}
