package jpize.tests.minecraftose.main.net.packet.serverbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.server.net.PlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class SBPacketChunkRequest extends IPacket<PlayerGameConnection>{
    
    public static final int PACKET_ID = 12;
    
    public SBPacketChunkRequest(){
        super(PACKET_ID);
    }
    
    public int chunkX, chunkZ;
    
    public SBPacketChunkRequest(int chunkX, int chunkZ){
        this();
        
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(chunkX);
        stream.writeInt(chunkZ);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        chunkX = stream.readInt();
        chunkZ = stream.readInt();
    }
    
    @Override
    public void handle(PlayerGameConnection handler){
        handler.chunkRequest(this);
    }
    
}
