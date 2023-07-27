package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public class CBPacketTime extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 22;
    
    public CBPacketTime(){
        super(PACKET_ID);
    }
    
    
    public long gameTimeTicks;
    
    public CBPacketTime(long gameTimeTicks){
        this();
        this.gameTimeTicks = gameTimeTicks;
    }
    
    
    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeLong(gameTimeTicks);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        gameTimeTicks = stream.readLong();
    }
    
}