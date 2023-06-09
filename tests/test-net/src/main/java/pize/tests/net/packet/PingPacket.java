package pize.tests.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PingPacket extends IPacket{
    
    public static final int PACKET_TYPE_ID = 18;
    
    public PingPacket(){
        super(PACKET_TYPE_ID);
    }
    
    
    private long time;
    
    public PingPacket(long time){
        this();
        this.time = time;
    }
    
    public long getTime(){
        return time;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeLong(time);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        time = stream.readLong();
    }
    
}
