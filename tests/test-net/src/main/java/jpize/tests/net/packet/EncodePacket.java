package jpize.tests.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.net.handler.MyPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class EncodePacket extends IPacket<MyPacketHandler>{
    
    public static final int PACKET_ID = 0;
    
    public EncodePacket(){
        super(PACKET_ID);
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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeByteArray(key);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        key = stream.readByteArray();
    }

    @Override
    public void handle(MyPacketHandler handler){
        handler.encode(this);
    }
    
}
