package pize.tests.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    protected void write(DataOutputStream stream) throws IOException{
        stream.write(key);
    }
    
    @Override
    public void read(DataInputStream stream) throws IOException{
        key = stream.readAllBytes();
    }
    
}
