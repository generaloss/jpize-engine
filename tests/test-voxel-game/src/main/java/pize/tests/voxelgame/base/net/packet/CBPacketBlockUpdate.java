package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class CBPacketBlockUpdate extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 10;
    
    public CBPacketBlockUpdate(){
        super(PACKET_ID);
    }
    
    public int x, y, z;
    public short state;
    
    public CBPacketBlockUpdate(int x, int y, int z, short state){
        this();
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = state;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeShort(state);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        x = stream.readInt();
        y = stream.readInt();
        z = stream.readInt();
        state = stream.readShort();
    }

}
