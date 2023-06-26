package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.net.PlayerConnectionAdapter;

import java.io.IOException;

public class SBPacketChunkRequest extends IPacket<PlayerConnectionAdapter>{
    
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
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeInt(chunkX);
        stream.writeInt(chunkZ);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        chunkX = stream.readInt();
        chunkZ = stream.readInt();
    }
    
    @Override
    public void handle(PlayerConnectionAdapter packetHandler){
        packetHandler.handleChunkRequest(this);
    }
    
}
