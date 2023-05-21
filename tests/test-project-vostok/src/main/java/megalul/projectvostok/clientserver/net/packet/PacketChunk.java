package megalul.projectvostok.clientserver.net.packet;

import java.io.Serializable;

public class PacketChunk implements Serializable{
    
    public final short[] blocks;
    public final short[] heightMap;
    public final byte[] skyLight;
    public final byte[] blockLight;
    public final short maxY;
    public final short blockCount;
    
    public PacketChunk(short[] blocks, short[] heightMap, byte[] skyLight, byte[] blockLight, short maxY, short blockCount){
        this.blocks = blocks;
        this.heightMap = heightMap;
        this.skyLight = skyLight;
        this.blockLight = blockLight;
        this.maxY = maxY;
        this.blockCount = blockCount;
    }
    
}
