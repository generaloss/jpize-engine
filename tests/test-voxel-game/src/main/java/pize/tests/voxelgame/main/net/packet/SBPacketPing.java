package pize.tests.voxelgame.main.net.packet;

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
    
    
    public long timeNanos;
    
    public SBPacketPing(long timeNanos){
        this();
        this.timeNanos = timeNanos;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeLong(timeNanos);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        timeNanos = stream.readLong();
    }
    
}
