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
    
    public String playerName;
    public int chunkX, chunkZ;
    
    public PacketChunkRequest(String playerName, int chunkX, int chunkZ){
        this();
        
        this.playerName = playerName;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(playerName);
        stream.writeInt(chunkX);
        stream.writeInt(chunkZ);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        playerName = stream.readUTF();
        chunkX = stream.readInt();
        chunkZ = stream.readInt();
    }
    
}
