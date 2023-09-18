package jpize.tests.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.net.handler.MyPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.*;

public class MessagePacket extends IPacket<MyPacketHandler>{
    
    public static final int PACKET_ID = 1;
    
    public MessagePacket(){
        super(PACKET_ID);
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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        message = stream.readUTF();
    }

    @Override
    public void handle(MyPacketHandler handler){
        handler.message(this);
    }
    
}
