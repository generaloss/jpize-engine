package jpize.tests.minecraftose.main.net.packet.serverbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.server.net.PlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class SBPacketRenderDistance extends IPacket<PlayerGameConnection>{
    
    public static final byte PACKET_ID = 8;
    
    public SBPacketRenderDistance(){
        super(PACKET_ID);
    }
    
    
    public int renderDistance;
    
    public SBPacketRenderDistance(int renderDistance){
        this();
        this.renderDistance = renderDistance;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(renderDistance);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        renderDistance = stream.readInt();
    }
    
    @Override
    public void handle(PlayerGameConnection handler){
        handler.renderDistance(this);
    }
    
}
