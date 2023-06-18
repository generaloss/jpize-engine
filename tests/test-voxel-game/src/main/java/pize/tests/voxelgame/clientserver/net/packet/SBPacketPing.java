package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class SBPacketPing extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 7;
    
    public SBPacketPing(){
        super(PACKET_ID);
    }
    
    
    public long timeMillis;
    
    public SBPacketPing(long timeMillis){
        this();
        this.timeMillis = timeMillis;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeLong(timeMillis);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        timeMillis = stream.readLong();
    }
    
}
