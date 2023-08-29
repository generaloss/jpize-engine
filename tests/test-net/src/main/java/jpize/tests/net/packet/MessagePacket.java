package jpize.tests.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.*;

public class MessagePacket extends IPacket{
    
    public static final int PACKET_TYPE_ID = 54;
    
    public MessagePacket(){
        super(PACKET_TYPE_ID);
    }
    
    
    private String message;
    
    public MessagePacket(String message){
        this();
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        message = stream.readUTF();
    }
    
}
