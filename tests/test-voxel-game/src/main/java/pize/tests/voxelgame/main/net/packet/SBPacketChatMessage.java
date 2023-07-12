package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.net.PlayerConnectionAdapter;

import java.io.IOException;

public class SBPacketChatMessage extends IPacket<PlayerConnectionAdapter>{
    
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
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        message = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerConnectionAdapter packetHandler){
        packetHandler.handleChatMessage(this);
    }
    
}