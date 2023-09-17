package jpize.tests.minecraftose.main.net.packet.serverbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.server.net.PlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class SBPacketChatMessage extends IPacket<PlayerGameConnection>{
    
    public static final byte PACKET_ID = 19;
    
    public SBPacketChatMessage(){
        super(PACKET_ID);
    }
    
    
    public String message;
    
    public SBPacketChatMessage(String message){
        this();
        this.message = message;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        message = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerGameConnection handler){
        handler.chatMessage(this);
    }
    
}