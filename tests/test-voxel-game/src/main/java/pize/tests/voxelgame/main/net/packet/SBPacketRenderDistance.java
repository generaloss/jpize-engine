package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.net.PlayerConnectionAdapter;

import java.io.IOException;

public class SBPacketRenderDistance extends IPacket<PlayerConnectionAdapter>{
    
    public static final int PACKET_ID = 8;
    
    public SBPacketRenderDistance(){
        super(PACKET_ID);
    }
    
    
    public int renderDistance;
    
    public SBPacketRenderDistance(int renderDistance){
        this();
        this.renderDistance = renderDistance;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeInt(renderDistance);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        renderDistance = stream.readInt();
    }
    
    @Override
    public void handle(PlayerConnectionAdapter packetHandler){
        packetHandler.handleRenderDistance(this);
    }
    
}
