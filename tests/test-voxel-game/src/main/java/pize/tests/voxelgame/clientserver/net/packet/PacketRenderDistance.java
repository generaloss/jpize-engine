package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketRenderDistance extends IPacket{
    
    public static final int PACKET_ID = 8;
    
    public PacketRenderDistance(){
        super(PACKET_ID);
    }
    
    
    public String playerName;
    public int renderDistance;
    
    public PacketRenderDistance(String playerName, int renderDistance){
        this();
        this.playerName = playerName;
        this.renderDistance = renderDistance;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(playerName);
        stream.writeInt(renderDistance);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        playerName = stream.readUTF();
        renderDistance = stream.readInt();
    }
    
}
