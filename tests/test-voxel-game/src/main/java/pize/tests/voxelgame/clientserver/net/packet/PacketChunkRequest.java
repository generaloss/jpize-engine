package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketChunkRequest extends IPacket{
    
    public static final int PACKET_ID = 12;
    
    public PacketChunkRequest(){
        super(PACKET_ID);
    }
    
    public int chunkX, chunkZ;
    
    public PacketChunkRequest(int chunkX, int chunkZ){
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
    
}
