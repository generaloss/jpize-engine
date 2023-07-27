package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.server.net.PlayerGameConnection;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeInt(chunkX);
        stream.writeInt(chunkZ);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        chunkX = stream.readInt();
        chunkZ = stream.readInt();
    }
    
    @Override
    public void handle(PlayerGameConnection packetHandler){
        packetHandler.handleChunkRequest(this);
    }
    
}
