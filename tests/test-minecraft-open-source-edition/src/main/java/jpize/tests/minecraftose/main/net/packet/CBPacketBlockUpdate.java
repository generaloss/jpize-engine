package jpize.tests.minecraftose.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketBlockUpdate extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 10;
    
    public CBPacketBlockUpdate(){
        super(PACKET_ID);
    }
    
    public int x, y, z;
    public short blockData;
    
    public CBPacketBlockUpdate(int x, int y, int z, short blockData){
        this();
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockData = blockData;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeShort(blockData);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        x = stream.readInt();
        y = stream.readInt();
        z = stream.readInt();
        blockData = stream.readShort();
    }

}
