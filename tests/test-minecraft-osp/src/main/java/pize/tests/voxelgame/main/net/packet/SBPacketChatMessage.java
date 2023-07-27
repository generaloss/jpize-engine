package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.server.net.PlayerGameConnection;

import java.io.IOException;

public class SBPacketChatMessage extends IPacket<PlayerGameConnection>{
    
    public static final int PACKET_ID = 19;
    
    public SBPacketChatMessage(){
        super(PACKET_ID);
    }
    
    
    public String message;
    
    public SBPacketChatMessage(String message){
        this();
        this.message = message;
    }
    
    
    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        message = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerGameConnection packetHandler){
        packetHandler.handleChatMessage(this);
    }
    
}