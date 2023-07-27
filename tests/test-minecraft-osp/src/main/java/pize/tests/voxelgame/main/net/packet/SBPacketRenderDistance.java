package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.server.net.PlayerGameConnection;

import java.io.IOException;

public class SBPacketRenderDistance extends IPacket<PlayerGameConnection>{
    
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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeInt(renderDistance);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        renderDistance = stream.readInt();
    }
    
    @Override
    public void handle(PlayerGameConnection packetHandler){
        packetHandler.handleRenderDistance(this);
    }
    
}
