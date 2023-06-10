package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketChunk extends IPacket{
    
    public static final int PACKET_ID = 2;
    
    public PacketChunk(){
        super(PACKET_ID);
    }
    
    public int chunkX, chunkZ;
    public short[] blocks;
    public short[] heightMap;
    public short maxY;
    public short blockCount;
    
    public PacketChunk(int chunkX, int chunkZ, short[] blocks, short[] heightMap, short maxY, short blockCount){
        this();
        
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.blocks = blocks;
        this.heightMap = heightMap;
        this.maxY = maxY;
        this.blockCount = blockCount;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeInt(chunkX);
        stream.writeInt(chunkZ);
        stream.writeShortArray(blocks);
        stream.writeShortArray(heightMap);
        stream.writeShort(maxY);
        stream.writeShort(blockCount);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        chunkX = stream.readInt();
        chunkZ = stream.readInt();
        blocks = stream.readShortArray();
        heightMap = stream.readShortArray();
        maxY = stream.readShort();
        blockCount = stream.readShort();
    }
    
}
