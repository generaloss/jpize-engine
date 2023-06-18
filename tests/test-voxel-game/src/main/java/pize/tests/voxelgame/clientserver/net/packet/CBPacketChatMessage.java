package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class CBPacketChatMessage extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 18;
    
    public CBPacketChatMessage(){
        super(PACKET_ID);
    }
    
    
    public String message;
    
    public CBPacketChatMessage(String message){
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
    
}