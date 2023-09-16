package jpize.tests.minecraftose.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(gameTimeTicks);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        gameTimeTicks = stream.readLong();
    }
    
}