package pize.tests.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        message = stream.readUTF();
    }
    
}
