package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public class CBPacketPong extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 7;
    
    public CBPacketPong(){
        super(PACKET_ID);
    }
    
    
    public long timeNanos;
    
    public CBPacketPong(long timeNanos){
        this();
        this.timeNanos = timeNanos;
    }
    
    
    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeLong(timeNanos);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        timeNanos = stream.readLong();
    }
    
}

